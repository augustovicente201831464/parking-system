package com.cunoc.edu.gt.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Custom class to handle the connection to the database
 *
 *
 * @Author: Augusto Vicente
 */
public class CustomizedConnection {

    public Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        if(connection == null){
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            String datasourceDriver = properties.getProperty("datasource.driver");
            String datasourceUrl = properties.getProperty("datasource.url");
            String datasourceUsername = properties.getProperty("datasource.username");
            String datasourcePassword = properties.getProperty("datasource.password");

            Class.forName(datasourceDriver);

            connection = DriverManager.getConnection(datasourceUrl, datasourceUsername, datasourcePassword);
        }

        return connection;
    }

    //Singleton pattern
    private CustomizedConnection() {
    }

    public static CustomizedConnection getInstance() {
        if (instance == null) {
            instance = new CustomizedConnection();
        }
        return instance;
    }

    private static CustomizedConnection instance;
    private static Connection connection;
}