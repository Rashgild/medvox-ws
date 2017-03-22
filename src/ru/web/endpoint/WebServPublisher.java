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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.Driver;
import ru.web.SQL.*;

public class WebServPublisher {

    public static String URL;

    public static String Name;
    public static String Password;
    public static String Ip;
    public static String Port;
    public static String Log;
    //public static int TypeLog;
    public static String PathSaveLog;


    public static void main(String... args) throws IOException {
        GetConfigurationXML();
        Endpoint.publish(Ip+":"+Port+"/webservice/start", new WebServiceImpl());
        ResultSet result = null;
        sout("Сервер запущен успешно!");
        sout("WSDL-файл по адресу "+Ip+":"+Port+"/webservice/start?wsdl");
        sout("Строка подключению к БД: "+URL);
        sout("Попытка подключения к БД...");

        try {result = sql_connect.SQL_Select("select id from Patient limit 1");}
        catch (Exception ex) {ex.printStackTrace();}
        finally {System.out.println("...Подключено!");}
        sout("Логгирование: "+Log);
    }

    private static void sout(String s)
    {
        System.out.println(s);
    }
    private static void GetConfigurationXML() throws IOException {

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = null;
        try {
            builder = f.newDocumentBuilder();

            Document doc = builder.parse(new File("configuration.xml"));
            NodeList nodeList = doc.getElementsByTagName("config");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    URL=element.getElementsByTagName("url").item(0).getTextContent();
                    Name=element.getElementsByTagName("name").item(0).getTextContent();
                    Password=element.getElementsByTagName("password").item(0).getTextContent();
                    Ip=element.getElementsByTagName("ip").item(0).getTextContent();
                    Port=element.getElementsByTagName("port").item(0).getTextContent();

                    Log=element.getElementsByTagName("log").item(0).getTextContent();
                    PathSaveLog=element.getElementsByTagName("PathSaveLog").item(0).getTextContent();
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            sout("Файл конфигурации не найден!");//e.printStackTrace();
        }

        /*if(Log.equalsIgnoreCase("extend"))TypeLog=2;
        if(Log.equalsIgnoreCase("short"))TypeLog=2;*/

    }





}


