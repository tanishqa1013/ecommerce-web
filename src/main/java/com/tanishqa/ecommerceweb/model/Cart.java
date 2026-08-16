package com.tanishqa.ecommerceweb.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    // Add a product to the cart
    // If product is already in cart, just increase the quantity
    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Update the quantity of an existing cart item
    public void updateQuantity(int productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                if (newQuantity <= 0) {
                    items.remove(item);
                } else {
                    item.setQuantity(newQuantity);
                }
                return;
            }
        }
    }

    // Remove a product from the cart
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    // Get all items in the cart
    public List<CartItem> getItems() {
        return items;
    }

    // Calculate the total price of everything in the cart
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Count total number of items (sum of all quantities)
    public int getItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    // Check if cart is empty
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Clear all items (used after successful checkout)
    public void clear() {
        items.clear();
    }
}