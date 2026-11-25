/*
 * package com.project.servlet;
 * 
 * import java.io.IOException; import java.sql.Connection; import
 * java.sql.Timestamp;
 * 
 * import com.project.daoimpl.OrderDaoImpl; import
 * com.project.daoimpl.OrderItemDaoImpl; import com.project.model.Cart; import
 * com.project.model.CartItem; import com.project.model.Order; import
 * com.project.model.OrderItem; import com.project.model.User;
 * 
 * import Util.DBConnection; import jakarta.servlet.ServletException; import
 * jakarta.servlet.annotation.WebServlet; import
 * jakarta.servlet.http.HttpServlet; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * jakarta.servlet.http.HttpSession;
 * 
 * @WebServlet("/checkout") public class CheckOutServlet extends HttpServlet {
 * 
 * @Override protected void service(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException {
 * 
 * Connection connection = DBConnection.getConnection();
 * 
 * HttpSession session = req.getSession();
 * 
 * Cart cart = (Cart)session.getAttribute("cart"); User user =
 * (User)session.getAttribute("user"); Integer restaurantId =
 * (Integer)session.getAttribute("restaurantId");
 * 
 * 
 * String paymentMethod = req.getParameter("paymentMethod"); String address =
 * req.getParameter("address");
 * 
 * if(user != null) {
 * 
 * if(cart != null && !cart.getAllItems().isEmpty()) {
 * 
 * double totalAmount = 0;
 * 
 * for (CartItem item : cart.getAllItems()) {
 * 
 * totalAmount += item.getPrice() * item.getQuantity();
 * 
 * }
 * 
 * Order order = new Order(user.getUserId(), restaurantId, new
 * Timestamp(System.currentTimeMillis()), totalAmount, "Pending", paymentMethod,
 * address);
 * 
 * 
 * 
 * OrderDaoImpl orderDaoImpl = new OrderDaoImpl(connection);
 * 
 * int orderID = orderDaoImpl.addOrder(order);
 * 
 * OrderItemDaoImpl orderItemDaoImpl = new OrderItemDaoImpl(connection);
 * 
 * for (CartItem item : cart.getAllItems()) {
 * 
 * int menuId = item.getItemId(); double price = item.getPrice(); int quantity =
 * item.getQuantity(); double totalPrice = item.getTotalPrice();
 * 
 * 
 * OrderItem orderItem = new OrderItem(orderID, menuId, quantity, totalPrice);
 * 
 * int result = orderItemDaoImpl.addOrderItem(orderItem);
 * 
 * if(result == 1) { resp.sendRedirect("order-success.html"); }else {
 * System.out.println("Failed to store data. "); }
 * 
 * 
 * }
 * 
 * }
 * 
 * else { resp.sendRedirect("callingRestaurant"); }
 * 
 * }
 * 
 * else { resp.sendRedirect("login.html"); }
 * 
 * 
 * 
 * }
 * 
 * }
 */










package com.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.project.daoimpl.OrderDaoImpl;
import com.project.model.Cart;
import com.project.model.CartItem;
import com.project.model.Order;
import com.project.model.OrderItem;
import com.project.model.User;

import Util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Connection connection = DBConnection.getConnection();
        HttpSession session = req.getSession();
        
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User)session.getAttribute("user");
        Integer restaurantId = (Integer)session.getAttribute("restaurantId");
        
        String paymentMethod = req.getParameter("paymentMethod");
        String address = req.getParameter("address");
        
        if (user != null) {
            if (cart != null && !cart.getAllItems().isEmpty()) {
            
                double totalAmount = 0;
                List<OrderItem> orderItemsList = new ArrayList<>();
                
                // 1. Calculate total AND build the OrderItem list
                for (CartItem item : cart.getAllItems()) {
                    totalAmount += item.getPrice() * item.getQuantity();
                    
                    OrderItem orderItem = new OrderItem();
                    orderItem.setMenuId(item.getItemId());
                    orderItem.setItemName(item.getItemName()); // Assuming CartItem has getName()
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    orderItem.calculateTotalPrice(item.getTotalPrice());
                    
                    orderItemsList.add(orderItem);
                }
                
                // 2. Create the Order and attach the items
                Order order = new Order(user.getUserId(), 
                        restaurantId, 
                        new Timestamp(System.currentTimeMillis()), 
                        totalAmount, 
                        "Pending", 
                        paymentMethod, 
                        address);
                
                order.setOrderItems(orderItemsList); // Crucial step missing previously!
                
                // 3. Save everything in a single transaction
                OrderDaoImpl orderDaoImpl = new OrderDaoImpl(connection);
                int orderID = orderDaoImpl.addOrder(order);
                
                // 4. Handle the redirect ONCE
                if (orderID > 0) {
                    // Clear the cart session here if desired: session.removeAttribute("cart");
                    resp.sendRedirect("order-success.html");
                } else {
                    System.out.println("Failed to store data.");
                    // Optional: resp.sendRedirect("checkout-failed.html");
                }
            
            } else {
                resp.sendRedirect("callingRestaurant");
            }
        } else {
            resp.sendRedirect("login.html");
        }
    }
}