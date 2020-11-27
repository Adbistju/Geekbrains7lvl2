package com.chat.DataSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private DataSource() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/chatusers",
                    "root",
                    "root"
            );
        } catch (SQLException throwables) {
            throw new RuntimeException("failed to establish connection", throwables);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new RuntimeException("could not close the connection", throwables);
        }
    }
}
