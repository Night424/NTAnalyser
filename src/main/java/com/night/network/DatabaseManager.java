package com.night.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:packets.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS packets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sourceIP TEXT,
                    destIP TEXT,
                    protocol TEXT,
                    length INTEGER,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                );
            """;
            conn.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPacket(String sourceIP, String destIP, String protocol, int length) {
        String insertSQL = "INSERT INTO packets (sourceIP, destIP, protocol, length) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, sourceIP);
            pstmt.setString(2, destIP);
            pstmt.setString(3, protocol);
            pstmt.setInt(4, length);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
