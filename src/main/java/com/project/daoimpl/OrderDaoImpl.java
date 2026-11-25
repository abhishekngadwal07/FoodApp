package com.project.daoimpl;

import com.project.dao.OrderDao;
import com.project.model.Order;
import com.project.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private Connection connection;

    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }

    // 🔥 INSERT ORDER + ORDER ITEMS (TRANSACTION SAFE)
    @Override
    public int addOrder(Order order) {

        int orderId = 0;

        String orderQuery = "INSERT INTO orders (user_id, restaurant_id, order_date, total_amount, status, payment_method, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, menu_id, item_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false); // 🔴 START TRANSACTION

            // 1️⃣ Insert into orders table
            PreparedStatement orderStmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);

            orderStmt.setInt(1, order.getUserId());
            orderStmt.setInt(2, order.getRestaurantId());
            orderStmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            orderStmt.setDouble(4, order.getTotalAmount());
            orderStmt.setString(5, order.getStatus());
            orderStmt.setString(6, order.getPaymentMethod());
            orderStmt.setString(7, order.getAddress());

            int rows = orderStmt.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // 2️⃣ Get generated orderId
            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // 3️⃣ Insert order items
            PreparedStatement itemStmt = connection.prepareStatement(orderItemQuery);

            for (OrderItem item : order.getOrderItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getMenuId());
                itemStmt.setString(3, item.getItemName());
                itemStmt.setInt(4, item.getQuantity());
                itemStmt.setDouble(5, item.getPrice());
                itemStmt.setDouble(6, item.getTotalPrice());

                itemStmt.addBatch();
            }

            itemStmt.executeBatch();

            // ✅ COMMIT if everything is successful
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback(); // ❌ ROLLBACK on failure
                System.out.println("Transaction Rolled Back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // restore default
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderId;
    }

    // 🔍 GET ORDER BY ID (with items)
    @Override
    public Order getOrderById(int orderId) {

        Order order = null;

        String orderQuery = "SELECT * FROM orders WHERE order_id = ?";
        String itemsQuery = "SELECT * FROM order_items WHERE order_id = ?";

        try {
            // 1️⃣ Fetch order
            PreparedStatement orderStmt = connection.prepareStatement(orderQuery);
            orderStmt.setInt(1, orderId);

            ResultSet rs = orderStmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setAddress(rs.getString("address"));
            }

            // 2️⃣ Fetch order items
            if (order != null) {
                PreparedStatement itemStmt = connection.prepareStatement(itemsQuery);
                itemStmt.setInt(1, orderId);

                ResultSet itemRs = itemStmt.executeQuery();
                List<OrderItem> items = new ArrayList<>();

                while (itemRs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderItemId(itemRs.getInt("order_item_id"));
                    item.setOrderId(itemRs.getInt("order_id"));
                    item.setMenuId(itemRs.getInt("menu_id"));
                    item.setItemName(itemRs.getString("item_name"));
                    item.setQuantity(itemRs.getInt("quantity"));
                    item.setPrice(itemRs.getDouble("price"));
                    item.calculateTotalPrice(itemRs.getDouble("total_price"));

                    items.add(item);
                }

                order.setOrderItems(items);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }
}