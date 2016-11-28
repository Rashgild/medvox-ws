package ru.web.service;
import ru.web.SQL.Sql_request;
import ru.web.SQL.sql_connect;
import ru.web.classes.*;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceClient;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;


@WebService(endpointInterface = "ru.web.service.IWebService")
public class WebServiceImpl implements IWebService {

    private Logger logger = Logger.getLogger(String.valueOf(WebServiceImpl.class));
    public static int tempid=1;
    @Override
    @WebMethod
    public ArrayOfPatient getArrayOfPatient(Integer birthday) throws IOException //получение списка специальностей
    {

      /*  // Инициализируем генератор
        Random rnd = new Random(System.currentTimeMillis());
// Получаем случайное число в диапазоне от min до max (включительно)
        int number = min + rnd.nextInt(1000 - min + 1);*/
        tempid = birthday;
        ArrayOfPatient arr = new ArrayOfPatient();
        List<Patient> patients = arr.getPatients();
        // Запрос на вывод специальности
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectPatients(birthday));
        Patient patient = new Patient();
        try {
            while(ResultSQLRequest.next())
            {
                patient = new Patient();
                patient.setId((ResultSQLRequest.getInt("id")));
                patient.setLastname((ResultSQLRequest.getString("lastname")));
                patient.setFirstname((ResultSQLRequest.getString("firstname")));
                patient.setMiddlename((ResultSQLRequest.getString("middlename")));
                patient.setBirthday((ResultSQLRequest.getString("birthday")));
                patients.add(patient);
            }
        }catch (Exception ex){}

        arr.setPatients(patients);

        //System.out.println (new java.util.Date ().toString () +" User enter "+birthday+" "+tempid+" "+logger.getResourceBundleName());



        //System.out.println(Sql_request.log[1]);
        //System.out.println(Sql_request.logger);

        if(Sql_request.logger !=0)
        {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfPatient(Integer birthday) " +
                            "\n Параметр birthday = " + birthday + "" +
                            "\n return:" + arr + "(");
           if(Sql_request.logger ==2) {
               for (Patient s : patients) {
                   WriteLogPlus("id: " + s.getId());
               }
           }
               WriteLogPlus(")\nEND}");

        }

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfSpecialization getArrayOfSpecialization() throws IOException //получение списка специальностей
    {
        ArrayOfSpecialization arr = new ArrayOfSpecialization();
        List<Specialization> specializations = arr.getSpecializations();
        // Запрос на вывод специальности
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectSpecialization());
        Specialization spec = new Specialization();
        try {
            while(ResultSQLRequest.next())
            {
               spec = new Specialization();
               spec.setId((ResultSQLRequest.getInt("id")));
               spec.setName((ResultSQLRequest.getString("name")));
               specializations.add(spec);
            }
        }catch (Exception ex){}

        arr.setSpecializations(specializations);


        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfSpecialization()" +
                            "\n return:" + arr + "(");
            if(Sql_request.logger ==2) {
            for (Specialization s : specializations) {
                WriteLogPlus("id: " + s.getId());
            }}
            WriteLogPlus(")\nEND}");
        }

        return arr;
    }
//TODO
    @Override
    @WebMethod
    public ArrayOfDoctor getArrayOfDoctor(Integer id_specialization) throws IOException//получение списка врачей
    {
        ArrayOfDoctor arr = new ArrayOfDoctor();
        List<Doctor> doctors = arr.getDoctors();
        //Запрос на вывод врачей
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDoctor(id_specialization));

        Doctor doc = new Doctor();
        try {
            while(ResultSQLRequest.next())
            {
                doc = new Doctor();
                doc.setId((ResultSQLRequest.getInt("id")));
                doc.setName((ResultSQLRequest.getString("name")));
                doc.setLastname((ResultSQLRequest.getString("lastname")));
                doc.setFirstname((ResultSQLRequest.getString("firstname")));
                doc.setMiddlename((ResultSQLRequest.getString("middlename")));
                doctors.add(doc);
            }
        }catch (Exception ex){}

        arr.setDoctors(doctors);



        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfDoctor(id_specialization)" +
                            "\n Параметр id_specialization = " + id_specialization + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (Doctor s : doctors) {
                    WriteLogPlus("id: " + s.getId());
                }
            }
            WriteLogPlus(")\nEND}");
        }

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesFirst(Integer id_specialization) throws IOException {
        ArrayOfDate arr = new ArrayOfDate();
        List<Date> dates = arr.getDates();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateFirst(id_specialization));
        Date dat = new Date();
        try {
            while(ResultSQLRequest.next())
            {
                dat = new Date();
                dat.setId((ResultSQLRequest.getInt("id")));
                dat.setDate((ResultSQLRequest.getString("date")));
                dates.add(dat);
            }
        }catch (Exception ex){}

        arr.setDates(dates);
        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfDatesFirst(id_specialization)" +
                            "\n Параметр id_specialization = " + id_specialization + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (Date s : dates) {
                    WriteLogPlus("id: " + s.getId() + " Дата: " + s.getDate());
                }
            }
            WriteLogPlus(")\nEND}");
        }

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) throws IOException {
        ArrayOfDate arr = new ArrayOfDate();
        List<Date> dates = arr.getDates();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateSecond(id_doctor,id_specialization));
        Date dat = new Date();
        try {
            while(ResultSQLRequest.next())
            {
                dat = new Date();
                dat.setId((ResultSQLRequest.getInt("id")));
                dat.setDate((ResultSQLRequest.getString("date")));
                dates.add(dat);
            }
        }catch (Exception ex){}

        arr.setDates(dates);
        //WriteLog("getArrayOfDatesSecond() parametrs:"+id_specialization+", "+id_doctor+" return:"+arr);
        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfDatesSecond(id_specialization, id_doctor)" +
                            "\n Параметр id_specialization = " + id_specialization + "" +
                            "\n Параметр id_doctor = " + id_doctor + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (Date s : dates) {
                    WriteLogPlus("id: " + s.getId() + " Дата: " + s.getDate());
                }
            }
            WriteLogPlus(")\nEND}");
        }

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_date) throws IOException {
        ArrayOfTime arr = new ArrayOfTime();
        List<Time> times = arr.getTimes();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeFirst(id_specialization, id_date));
        Time tim = new Time();
        try {
            while(ResultSQLRequest.next())
            {
                tim = new Time();
                tim.setId((ResultSQLRequest.getInt("id")));
                tim.setTime((ResultSQLRequest.getString("time")));
                times.add(tim);
            }
        }catch (Exception ex){}

       // WriteLog("getArrayOfTimesFirst() parametrs:"+id_specialization+", "+id_date+" return:"+arr);
        arr.setTimes(times);
        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfTimesFirst(id_specialization, id_date)" +
                            "\n Параметр id_specialization = " + id_specialization + "" +
                            "\n Параметр id_date = " + id_date + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (Time s : times) {
                    WriteLogPlus("id: " + s.getId() + " Время: " + s.getTime());
                }
            }
            WriteLogPlus(")\nEND}");
        }

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesSecond(Integer id_date) throws IOException {
        ArrayOfTime arr = new ArrayOfTime();
        List<Time> times = arr.getTimes();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeSecond(id_date));
        Time tim = new Time();
        try {
            while(ResultSQLRequest.next())
            {
                tim = new Time();
                tim.setId((ResultSQLRequest.getInt("id")));
                tim.setTime((ResultSQLRequest.getString("time")));
                times.add(tim);
            }
        }catch (Exception ex){}

        arr.setTimes(times);

        //WriteLog("getArrayOfTimesSecond() parametrs:"+id_date+" return:"+arr);
        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfTimesSecond(id_date)" +
                            "\n Параметр id_date = " + id_date + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (Time s : times) {
                    WriteLogPlus("id: " + s.getId() + " Время: " + s.getTime());
                }
            }
            WriteLogPlus(")\nEND}");
        }
        return arr;
    }

/*
    public String getAllInformation(Integer id_doc, Integer id_spec, Integer id_date, Integer id_time) throws IOException {
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectAllInformation(id_time));
        String SQL_res="";
        try {

            while(ResultSQLRequest.next())
            {

                    SQL_res += ResultSQLRequest.getString("name")+" "+ResultSQLRequest.getString("lastname")+
                    " "+ResultSQLRequest.getString("firstname")+" "+ResultSQLRequest.getString("middlename")+
                    " на "+ResultSQLRequest.getString("date")+" "+ResultSQLRequest.getString("time");
            }


        }catch (Exception ex){}
        WriteLog("getAllInformation() parametrs:"+id_doc+", "+id_spec+", "+id_date+", "+id_time+" return:"+SQL_res);
        return SQL_res;
    }*/

    @Override
    @WebMethod
    public ArrayOfAllinformation getArrayOfAllinformation(Integer id_time) throws IOException
    {
        ArrayOfAllinformation arr = new ArrayOfAllinformation();
        List<AllInformation> allinfs = arr.getAllinformation();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectAllInformation(id_time));
        String SQL_res="";

        AllInformation alinf = new AllInformation();
        //Doctor doc = new Doctor();
        try {
            while(ResultSQLRequest.next())
            {
                alinf = new AllInformation();
                alinf.setName((ResultSQLRequest.getString("name")));
                alinf.setLastname((ResultSQLRequest.getString("lastname")));
                alinf.setFirstname((ResultSQLRequest.getString("firstname")));
                alinf.setMiddlename((ResultSQLRequest.getString("middlename")));
                alinf.setDate((ResultSQLRequest.getString("date")));
                alinf.setTime((ResultSQLRequest.getString("time")));
                allinfs.add(alinf);
            }
        }catch (
                //System.out.println()
            Exception ex)

        {}

        arr.setAllinfomation(allinfs);

        //WriteLog("getArrayOfDoctor() parametrs:"+id_specialization+" return:"+arr);
        //WriteLog("getArrayOfAllinformation() parametrs: "+id_time+" return:"+arr);
        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: getArrayOfAllinformation(id_time)" +
                            "\n Параметр id_time = " + id_time + "" +
                            "\n return:" + arr + "(");
            if (Sql_request.logger == 2) {
                for (AllInformation s : allinfs) {
                    WriteLogPlus("Специализация: " + s.getName() + "ФИО врача" + s.getLastname() + " " + s.getFirstname() +
                            " " + s.getMiddlename() + " Дата " + s.getDate() + " Время: " + s.getTime());
                }
            }
            WriteLogPlus(")\nEND}");
        }
        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time) throws IOException {

        //CheckTimeCalendar(id_time);
        System.out.println(CheckTimeCalendar(id_time));
        if(CheckTimeCalendar(id_time)==0)
        {

            ResultSet res = sql_connect.SQL_UpdIns(Sql_request.UpdatePatient(id_patient,id_time));
            int res_str = 0;
            try {
                while (res.next()) {
                    res_str = res.getInt("id");
                }
            } catch (Exception ex) {
            }


            if (res_str > 0) {
                // WriteLog("setPatientRecord() parametrs:"+id_patient+", "+id_time+" return:"+res_str);

                if (Sql_request.logger != 0) {
                    WriteLog(
                            "{\nSTART:" +
                                    "\n Вызван: setPatientRecord(id_patient,id_time)" +
                                    "\n Параметр id_time = " + id_time + "" +
                                    "\n Параметр id_time = " + id_patient + "" +
                                    "\n return:" + res_str + "(");
                    WriteLogPlus(")\nEND}");
                }
                return getArrayOfAllinformation(id_time);

            } else {
                return null;//getArrayOfAllinformation(id_time);
            }
        }else return null;//getArrayOfAllinformation(id_time);

        //return res_str;
    }

    public int CheckTimeCalendar(Integer id_time) throws IOException {

        //if()
        ResultSet res = sql_connect.SQL_Select(Sql_request.CheckPatientEmpty(id_time));
        int res_str=0;
        try {
            while(res.next())
            {
                res_str = res.getInt("prepatient_id");
            }
        }catch (Exception ex){}
        //WriteLog("CheckTimeCalendar() parametrs:"+id_time+" return:"+res_str);

        if(Sql_request.logger !=0) {
            WriteLog(
                    "{\nSTART:" +
                            "\n Вызван: CheckTimeCalendar()" +
                            "\n Параметр id_time = " + id_time + "" +
                            "\n return:" + res_str + "(");
            WriteLogPlus(")\nEND}");
        }
        return res_str;


    }

    public static void WriteLog(String S) throws IOException {

        FileWriter wr = new FileWriter("log.txt", true);
        wr.append(new java.util.Date ().toString ()+" "+S);
        wr.append("\n");
        wr.flush();
        wr.close();
    }

    public static void WriteLogPlus(String S) throws IOException {

        FileWriter wr = new FileWriter("log.txt", true);
        wr.append(S);
        wr.append("\n");
        wr.flush();
        wr.close();
    }


public static void MyLogger(String nameMeth, String nameParametr, Integer parametr, String arr, Class ss, List<Class> ar)
        throws IOException {
    if(Sql_request.logger !=0) {

        WriteLog("{\nSTART:");
        WriteLogPlus("\n Вызван: "+nameMeth);
        WriteLogPlus("\n Параметр "+nameParametr+" = "+parametr);
        WriteLogPlus("\n return:" + arr + "(");
        if (Sql_request.logger == 2) {

        }
        WriteLogPlus(")\nEND}");

    }
}


    private static Boolean CheckInput(String str)
    {
            if ((str.matches("^\\d+$"))) {
                return true;
            }
        return false;
    }

}






