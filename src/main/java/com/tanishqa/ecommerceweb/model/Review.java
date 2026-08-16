package com.tanishqa.ecommerceweb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    private int id;
    private int productId;
    private int userId;
    private String username;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review() {}

    public Review(int productId, int userId, String username, int rating, String comment) {
        this.productId = productId;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(int id) { this.id = id; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getFormattedDate() {
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
}