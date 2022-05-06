package com.spring.project.development.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseService {
    @Autowired
    private Environment env;

    public Connection getConnection() throws SQLException {
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        return DriverManager.getConnection(url, username, password);
    }
}
