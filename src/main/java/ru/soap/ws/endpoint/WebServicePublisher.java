package ru.soap.ws.endpoint;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;
import javax.xml.ws.Endpoint;

import ru.soap.ws.utils.SqlConnect;
import ru.soap.ws.service.WebServiceImpl;

public class WebServicePublisher {

    public static String host, login, password, ip, port, logging, pathSave, key;

    public static void main(String... args) {
        propLoad();
        Endpoint.publish(ip + ":" + port + "/", new WebServiceImpl());
        ResultSet result = null;
        System.out.println("Сервер запущен успешно!");
        System.out.println("WSDL-файл по адресу " + ip + ":" + port + "/webservice/start?wsdl");
        System.out.println("Строка подключению к БД: " + host);
        System.out.println("Попытка подключения к БД...");

        try {
            result = SqlConnect.SQL_Select("select id from Patient limit 1");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("...Подключено!");
        }
        System.out.println("Логгирование: " + logging);
    }


    /**
     * Загрузка файла-конфигурации
     */
    private static void propLoad() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/properties/config.properties");
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");
            ip = property.getProperty("ws.ip");
            port = property.getProperty("ws.port");
            logging = property.getProperty("log.logging");
            pathSave = property.getProperty("log.pathSave");
            key = property.getProperty("key.key");
        } catch (IOException e) {
            System.out.println("Файл конфигурации не найден!");//e.printStackTrace();
        }
    }
}


