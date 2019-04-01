package ru.soap.ws.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.soap.ws.service.WebServiceImpl;

public class SqlConnect {

    public static ResultSet SQL_Select(String query) {
        ResultSet result = null;
        try {
            Class.forName("org.postgresql.Driver");
            PropertiesReader prop = new PropertiesReader();
            String host = prop.properties.getProperty("db.host");
            String login = prop.properties.getProperty("db.login");
            String password = prop.properties.getProperty("db.password");

            Connection connection = DriverManager.getConnection(host, login, password);
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
            connection.close();
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
