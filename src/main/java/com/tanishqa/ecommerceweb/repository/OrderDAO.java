package com.tanishqa.ecommerceweb.repository;

import com.tanishqa.ecommerceweb.model.CartItem;
import com.tanishqa.ecommerceweb.model.Order;
import com.tanishqa.ecommerceweb.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Save a complete order (order + all its items + reduce stock)
    // Uses a transaction so if ANY step fails, NOTHING is saved
    public int placeOrder(Order order, List<CartItem> cartItems) {

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Step 1: Insert into orders table
            String orderSql =
                    "INSERT INTO orders " +
                    "(user_id, total, status, shipping_name, shipping_address, shipping_phone, payment_method) " +
                    "VALUES (?, ?, 'PLACED', ?, ?, ?, ?)";

            int orderId;

            try (PreparedStatement stmt =
                         conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, order.getUserId());
                stmt.setDouble(2, order.getTotal());
                stmt.setString(3, order.getShippingName());
                stmt.setString(4, order.getShippingAddress());
                stmt.setString(5, order.getShippingPhone());
                stmt.setString(6, order.getPaymentMethod());

                stmt.executeUpdate();

                // Get generated order ID
                try (ResultSet keys = stmt.getGeneratedKeys()) {

                    if (keys.next()) {
                        orderId = keys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get order ID");
                    }
                }
            }

            // Step 2: Insert order items
            String itemSql =
                    "INSERT INTO order_items " +
                    "(order_id, product_id, quantity, price) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(itemSql)) {

                for (CartItem item : cartItems) {

                    stmt.setInt(1, orderId);
                    stmt.setInt(2, item.getProduct().getId());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setDouble(4, item.getProduct().getPrice());

                    stmt.addBatch();
                }

                stmt.executeBatch();
            }

            // Step 3: Reduce stock
            String stockSql =
                    "UPDATE products SET stock = stock - ? WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(stockSql)) {

                for (CartItem item : cartItems) {

                    stmt.setInt(1, item.getQuantity());
                    stmt.setInt(2, item.getProduct().getId());

                    stmt.addBatch();
                }

                stmt.executeBatch();
            }

            conn.commit();

            return orderId;

        } catch (SQLException e) {

            e.printStackTrace();

            if (conn != null) {

                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return -1;

        } finally {

            if (conn != null) {

                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Fetch a single order by ID
    // Used by the order confirmation page
    public Order getOrderById(int orderId) {

        String sql =
                "SELECT o.*, u.username " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE o.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Order o = new Order();

                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setUsername(rs.getString("username"));
                    o.setTotal(rs.getDouble("total"));

                    Timestamp ts = rs.getTimestamp("order_date");

                    if (ts != null) {
                        o.setOrderDate(ts.toLocalDateTime());
                    }

                    o.setStatus(rs.getString("status"));
                    o.setShippingName(rs.getString("shipping_name"));
                    o.setShippingAddress(rs.getString("shipping_address"));
                    o.setShippingPhone(rs.getString("shipping_phone"));
                    o.setPaymentMethod(rs.getString("payment_method"));

                    return o;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Fetch ALL orders belonging to one specific user
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orders = new ArrayList<>();

        String sql =
                "SELECT o.*, u.username " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "WHERE o.user_id = ? " +
                "ORDER BY o.order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Order o = new Order();

                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setUsername(rs.getString("username"));
                    o.setTotal(rs.getDouble("total"));

                    Timestamp ts = rs.getTimestamp("order_date");

                    if (ts != null) {
                        o.setOrderDate(ts.toLocalDateTime());
                    }

                    o.setStatus(rs.getString("status"));
                    o.setShippingName(rs.getString("shipping_name"));
                    o.setShippingAddress(rs.getString("shipping_address"));
                    o.setShippingPhone(rs.getString("shipping_phone"));
                    o.setPaymentMethod(rs.getString("payment_method"));

                    orders.add(o);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}