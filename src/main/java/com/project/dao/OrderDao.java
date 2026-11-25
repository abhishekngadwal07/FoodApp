package com.project.dao;

import com.project.model.Order;

public interface OrderDao {

    // Create a new order with items
    int addOrder(Order order);

    // Get order by ID
    Order getOrderById(int orderId);
}