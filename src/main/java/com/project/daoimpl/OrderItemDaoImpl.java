package com.project.daoimpl;

import com.project.dao.OrderItemDao;
import com.project.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {

    private Connection connection;

    public OrderItemDaoImpl(Connection connection) {
        this.connection = connection;
    }

    // 🔹 Insert single item
    @Override
    public int addOrderItem(OrderItem item) {

        String query = "INSERT INTO order_items (order_id, menu_id, item_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuId());
            ps.setString(3, item.getItemName());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getPrice());
            ps.setDouble(6, item.getTotalPrice());

            int result = ps.executeUpdate();
            
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
        
    }

    // 🔥 Batch insert (used in Order flow)
    @Override
    public void addOrderItems(List<OrderItem> items, int orderId) {

        String query = "INSERT INTO order_items (order_id, menu_id, item_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            for (OrderItem item : items) {
                ps.setInt(1, orderId); // use generated orderId
                ps.setInt(2, item.getMenuId());
                ps.setString(3, item.getItemName());
                ps.setInt(4, item.getQuantity());
                ps.setDouble(5, item.getPrice());
                ps.setDouble(6, item.getTotalPrice());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔍 Fetch items by orderId
    @Override
    public List<OrderItem> getItemsByOrderId(int orderId) {

        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();

                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.calculateTotalPrice(rs.getDouble("total_price"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}