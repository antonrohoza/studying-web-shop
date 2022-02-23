package com.antonr.webshop.config;

import com.antonr.webshop.web.util.PageGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConnectionFactory {
    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class);
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(Properties properties) {
        this.url = properties.getProperty("db_url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOG.error("Can't set connection by using url:" + url + "!", e);
            throw new RuntimeException(e);
        }
    }
}
