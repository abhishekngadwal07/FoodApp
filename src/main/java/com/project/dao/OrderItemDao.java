package com.project.dao;

import com.project.model.OrderItem;
import java.util.List;

public interface OrderItemDao {

    // Add single item
    int addOrderItem(OrderItem item);

    // Add multiple items (batch)
    void addOrderItems(List<OrderItem> items, int orderId);

    // Get items by orderId
    List<OrderItem> getItemsByOrderId(int orderId);
}