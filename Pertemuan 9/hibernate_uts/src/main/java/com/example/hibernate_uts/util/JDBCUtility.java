package com.example.hibernate_uts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {
    public static Connection getConnection() {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/utssa",
                    "root",
                    ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }
}
