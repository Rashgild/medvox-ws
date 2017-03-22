package ru.web.SQL;

import ru.web.endpoint.WebServPublisher;
import ru.web.service.WebServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.web.SQL.*;

/**
 * Created by rkurbanov on 01.08.16.
 */


public class sql_connect {

    public static ResultSet SQL_Select (String query)
    {
        Connection connection = null;
        ResultSet result = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(WebServPublisher.URL, WebServPublisher.Name, WebServPublisher.Password);
            Statement statement = null;
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            connection.close();
            return result;

        } catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}
        return result;
    }

    /*public static ResultSet SQL_UpdIns (String s)
    {
        Connection connection = null;
        ResultSet result= null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(WebServPublisher.URL, WebServPublisher.Name, WebServPublisher.Password);
            Statement statement = null;
            statement = connection.createStatement();

            result  =   statement.executeQuery(s);
            connection.close();
           return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}
        return result;
    }*/
}
