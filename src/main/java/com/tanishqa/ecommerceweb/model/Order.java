package com.tanishqa.ecommerceweb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String username;
    private double total;
    private LocalDateTime orderDate;
    private String status;
    private String shippingName;
    private String shippingAddress;
    private String shippingPhone;
    private String paymentMethod;
    private List<CartItem> items = new ArrayList<>();

    public Order() {}

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public double getTotal() { return total; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public String getShippingName() { return shippingName; }
    public String getShippingAddress() { return shippingAddress; }
    public String getShippingPhone() { return shippingPhone; }
    public String getPaymentMethod() { return paymentMethod; }
    public List<CartItem> getItems() { return items; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setTotal(double total) { this.total = total; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setStatus(String status) { this.status = status; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setItems(List<CartItem> items) { this.items = items; }

    // Helper: format date for display
    public String getFormattedDate() {
        if (orderDate == null) return "";
        return orderDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }
}