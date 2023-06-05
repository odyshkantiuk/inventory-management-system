package com.inventory_management_system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * The DBUtil class provides utility methods for handling database connections.
 */
public class DBUtil {

    private static Connection connection = null;

    /**
     * Retrieves the database connection.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/inventory_management_system_db";
                String user = "root";
                String password = "210872Qw";
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}