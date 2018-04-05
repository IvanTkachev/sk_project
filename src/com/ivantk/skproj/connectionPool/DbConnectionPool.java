package com.ivantk.skproj.connectionPool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * The class that creates the connection to the database
 */
public class DbConnectionPool {


    private String url;
    private String user;
    private String password;


    private static DbConnectionPool instance;

    private Deque<Connection> connections;

    private DbConnectionPool() {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        url = resourceBundle.getString("jdbc.url");
        user = resourceBundle.getString("jdbc.username");
        password = resourceBundle.getString("jdbc.password");
        this.connections = new LinkedList<>();

    }

    /**
     * Function that returns a DbConnectionPool#connection
     */
    public synchronized Connection getConnection() {
        if (!connections.isEmpty()) {
            return connections.poll();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Function that close a DbConnectionPool#connection
     */
    public void footConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connections.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DbConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DbConnectionPool.class) {
                instance = new DbConnectionPool();
                return instance;
            }
        }
        return instance;
    }
}


