
package com.tanishqa.ecommerceweb.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private String imageUrl;
    private List<String> images = new ArrayList<>();

    public Product() {}

    public Product(int id, String name, String description, double price,
                   int stock, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public List<String> getImages() { return images; }

    // Returns the first image (used on grid view)
    public String getMainImage() {
        if (images != null && !images.isEmpty()) {
            return images.get(0);
        }
        return imageUrl;  // fallback to old imageUrl
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setCategory(String category) { this.category = category; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setImages(List<String> images) { this.images = images; }

    // Helper method to add a single image
    public void addImage(String imagePath) {
        this.images.add(imagePath);
    }
}