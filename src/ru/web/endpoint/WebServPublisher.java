package ru.web.endpoint;
/**
 * Created by rkurbanov on 01.08.16.
 */

import javax.xml.ws.Endpoint;

import ru.web.SQL.Sql_request;
import ru.web.SQL.sql_connect;
import ru.web.service.WebServiceImpl;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.Driver;
import ru.web.SQL.*;

public class WebServPublisher {
    public static void main(String... args) throws IOException {
        // запускаем веб-сервер на порту 1986
        // и по адресу, указанному в первом аргументе,
        // запускаем веб-сервис, передаваемый во втором аргументе
        //Endpoint.publish("http://192.168.4.21:1986/webservice/start", new WebServiceImpl());
        GetConfiguration();
        Endpoint.publish(Sql_request.Ip[1]+ Sql_request.Port[1]+"/webservice/start", new WebServiceImpl());

       /* System.out.println(Sql_request.URL[1]);
        System.out.println(Sql_request.Name[1]);
        System.out.println(Sql_request.Pwd[1]);*/
        ResultSet result = null;
       System.out.println("Сервер запущен успешно!");
       System.out.println("WSDL-файл по адресу "+Sql_request.Ip[1]+ Sql_request.Port[1]+"/webservice/start?wsdl");
        System.out.println("Строка подключению к БД: "+Sql_request.URL[1]);
        System.out.println("Попытка подключения к БД...");

        try {
            result = sql_connect.SQL_Select("select id from Patient limit 1");
            System.out.println("...Подключено!");
        }catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(WebServiceImpl.class.getName()).log(Level.SEVERE, null, ex);}

        Printline("Логгирование: "+Sql_request.log[1]+" ::"+Sql_request.logger);

        }


    private static void GetConfiguration() throws IOException {

        InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("configuration.conf");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inp));

        String line;


        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        String strin = lines.get(0);
        Sql_request.URL = strin.split("=");
        strin = lines.get(1);
        Sql_request.Name = strin.split("=");
        strin = lines.get(2);
        Sql_request.Pwd = strin.split("=");
        strin = lines.get(3);
        Sql_request.Ip = strin.split("=");
        strin = lines.get(4);
        Sql_request.Port = strin.split("=");
        strin = lines.get(5);
        Sql_request.log = strin.split("=");

 //       if(Sql_request.log ="extend")

        if(Sql_request.log[1].equalsIgnoreCase("extend"))
        {
            Sql_request.logger=2;
        }
        if(Sql_request.log[1].equalsIgnoreCase("short"))
        {
            Sql_request.logger=1;
        }

        // ="extend")
        if(Sql_request.Pwd[1].equalsIgnoreCase("null"))
        {
            Sql_request.Pwd[1] = "";
        }
    }

    private static void Printline(String s)
    {
        System.out.println(s);
    }

}


