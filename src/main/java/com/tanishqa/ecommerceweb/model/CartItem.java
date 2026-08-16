package com.tanishqa.ecommerceweb.model;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem() {}

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // Setters
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Calculates: price × quantity
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}