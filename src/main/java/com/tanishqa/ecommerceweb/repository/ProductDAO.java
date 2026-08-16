package com.tanishqa.ecommerceweb.repository;

import com.tanishqa.ecommerceweb.model.Product;
import com.tanishqa.ecommerceweb.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Fetch all products from the database, with their images
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = mapProduct(rs);
                loadImages(conn, p);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Fetch one product by its ID, with its images
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product p = mapProduct(rs);
                    loadImages(conn, p);
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Helper: convert a database row into a Product object
    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setStock(rs.getInt("stock"));
        p.setCategory(rs.getString("category"));
        p.setImageUrl(rs.getString("image_url"));
        return p;
    }

    // Helper: load all images for a given product
    private void loadImages(Connection conn, Product product) throws SQLException {
        String sql = "SELECT image_path FROM product_images WHERE product_id = ? ORDER BY display_order";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    product.addImage(rs.getString("image_path"));
                }
            }
        }
    }
}