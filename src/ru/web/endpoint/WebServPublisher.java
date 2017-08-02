package ru.web.endpoint;
/**
 * Created by rkurbanov on 01.08.16.
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Endpoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.web.SQL.Sql_request;
import ru.web.SQL.sql_connect;
import ru.web.service.WebServiceImpl;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.Driver;
import ru.web.SQL.*;

public class WebServPublisher {

   /* public static String URL;
    public static String Name;
    public static String Password;
    public static String Ip;
    public static String Port;
    public static String Log;
    //public static int TypeLog;
    public static String PathSaveLog;*/

   public static String host,login,password,ip,port,logging,pathSave,key;
/*
#настройка jdbc
db.host = jdbc:postgresql://192.168.10.2:5432/riams
db.login = postgres
db.password ="";

#настройка сервиса
ws.ip= http://192.168.2.45;
ws.port

#настройка логгера
log.logging = 1
log.pathSave = root/Server

 */



    public static void main(String... args) throws IOException {

        propLoad();
        //Endpoint.publish(ip+":"+port+"/webservice/start", new WebServiceImpl());
        Endpoint.publish(ip+":"+port+"/", new WebServiceImpl());
        ResultSet result = null;
        sout("Сервер запущен успешно!");
        sout("WSDL-файл по адресу "+ip+":"+port+"/webservice/start?wsdl");
        sout("Строка подключению к БД: "+host);
        sout("Попытка подключения к БД...");
        try {result = sql_connect.SQL_Select("select id from Patient limit 1");}
        catch (Exception ex) {ex.printStackTrace();}
        finally {System.out.println("...Подключено!");}
        sout("Логгирование: "+logging);

    }



    /**
     * Загрузка файла-конфигурации
     */
    public static void propLoad()
    {
        FileInputStream fis = null;
        Properties property = new Properties();

        try {
              //fis = new FileInputStream("config.properties");
            fis = new FileInputStream("src/properties/config.properties");
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");
            ip = property.getProperty("ws.ip");
            port = property.getProperty("ws.port");
            logging = property.getProperty("log.logging");
            pathSave= property.getProperty("log.pathSave");
            key=property.getProperty("key.key");
        } catch (IOException e) {
            sout("Файл конфигурации не найден!");//e.printStackTrace();
        }
    }

    private static void sout(String s)
    {
        System.out.println(s);
    }
}


