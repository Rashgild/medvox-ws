package ru.soap.ws.endpoint;

import java.sql.ResultSet;
import javax.xml.ws.Endpoint;

import ru.soap.ws.service.WebServiceImpl;
import ru.soap.ws.utils.PropertiesReader;
import ru.soap.ws.utils.SqlConnect;

public class WebServicePublisher {

    public static void main(String... args) {
        PropertiesReader prop = new PropertiesReader();
        String ip = prop.properties.getProperty("ws.ip");
        String port = prop.properties.getProperty("ws.port");
        String db = prop.properties.getProperty("db.host");

        Endpoint.publish(ip + ":" + port + "/", new WebServiceImpl());
        ResultSet result = null;
        System.out.println("Сервер запущен успешно!");
        System.out.println("WSDL-файл по адресу " + ip + ":" + port + "/webservice/start?wsdl");
        System.out.println("Строка подключению к БД: " + db);
        System.out.println("Попытка подключения к БД...");

        try {
            result = SqlConnect.SQL_Select("select id from Patient limit 1");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("...Подключено!");
        }
    }
}
