package ru.web.SQL;

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

    public static ResultSet SQL_Select (String s)
    {
        Connection connection = null;
        //String url = "jdbc:postgresql://127.0.0.1:5432/TestDB";
        String url = Sql_request.URL[1];
        String name =Sql_request.Name[1];
        String password =Sql_request.Pwd[1];
        ResultSet result1 = null;

        try {
            Class.forName("org.postgresql.Driver");
            //System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, name, password);
            //System.out.println("Соединение установлено");
            Statement statement = null;
            statement = connection.createStatement();
            //Выполним запрос
             result1 = statement.executeQuery(s);
            String N="";
            connection.close();
            return result1;

        } catch (Exception ex)
        {
            ex.printStackTrace();
            //выводим наиболее значимые сообщения
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}
        return result1;
    }

    public static ResultSet SQL_UpdIns (String s)
    {
        Connection connection = null;
        //String url = "jdbc:postgresql://127.0.0.1:5432/TestDB";
        String url = Sql_request.URL[1];
        String name =Sql_request.Name[1];
        String password =Sql_request.Pwd[1];
        ResultSet res= null;
        try {
            Class.forName("org.postgresql.Driver");
        //System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, name, password);
            //System.out.println("Соединение установлено");
            Statement statement = null;
            statement = connection.createStatement();

            res  =   statement.executeQuery(s);
            connection.close();
           return res;

        } catch (Exception ex) {
            ex.printStackTrace();
            //выводим наиболее значимые сообщения
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}
        return res;
    }
}
