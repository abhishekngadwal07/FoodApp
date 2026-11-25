package com.project.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class Cart {

    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    // ✅ Add Item
    public void addItem(CartItem item) {
        int itemId = item.getItemId();

        if (items.containsKey(itemId)) {
            CartItem existingItem = items.get(itemId);
            existingItem.setQuantity(
                existingItem.getQuantity() + item.getQuantity()
            );
        } else {
            items.put(itemId, item);
        }
    }

    // ✅ Update Item Quantity
    public void updateItem(int itemId, int quantity) {
        if (items.containsKey(itemId)) {
            if (quantity <= 0) {
                items.remove(itemId);
            } else {
                items.get(itemId).setQuantity(quantity);
            }
        }
    }

    // ✅ Delete Item
    public void deleteItem(int itemId) {
        items.remove(itemId);
    }

    // ✅ Get Single Item
    public CartItem getItem(int itemId) {
        return items.get(itemId);
    }

    // ✅ Get All Items
    public Collection<CartItem> getAllItems() {
        return items.values();
    }

    // ✅ Get Total Cart Price
    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // ✅ Clear Cart
    public void clearCart() {
        items.clear();
    }

    // ✅ Check if Cart is Empty
    public boolean isEmpty() {
        return items.isEmpty();
    }
}