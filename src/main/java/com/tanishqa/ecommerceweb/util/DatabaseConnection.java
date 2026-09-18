package com.tanishqa.ecommerceweb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Reads from environment variables (Render/Aiven) or falls back to local MySQL
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        // Try environment variables first (for cloud deployment)
        String envUrl = System.getenv("DB_URL");
        String envUser = System.getenv("DB_USER");
        String envPass = System.getenv("DB_PASSWORD");

        if (envUrl != null && !envUrl.isEmpty()) {
            URL = envUrl;
            USERNAME = envUser;
            PASSWORD = envPass;
            System.out.println("[DatabaseConnection] Using CLOUD database");
        } else {
            // Fallback to local MySQL
            URL = "jdbc:mysql://localhost:3306/ecommerce_db";
            USERNAME = "root";
            PASSWORD = "Admin@2815";
            System.out.println("[DatabaseConnection] Using LOCAL database");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}