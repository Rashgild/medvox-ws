package ru.web.service;
import ru.web.SQL.Sql_request;
import ru.web.SQL.sql_connect;
import ru.web.classes.*;
import ru.web.classes.Date;
import ru.web.endpoint.WebServPublisher;

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@WebService(endpointInterface = "ru.web.service.IWebService")
public class WebServiceImpl implements IWebService {


    public static int tempid=1;
    @Override
    @WebMethod
    public ArrayOfPatient getArrayOfPatient(Integer birthday) //получение списка специальностей
    {
        tempid = birthday;
        ArrayOfPatient arr = new ArrayOfPatient();
        List<Patient> patients = arr.getPatients();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setPatients(patients);

        /**-----LogWriter*/
        String params=getParam("Integer birthday",birthday.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }


    @Override
    @WebMethod
    public ArrayOfSpecialization getArrayOfSpecialization()  //получение списка специальностей
    {
        return getArrayOfSpecializationExtend(180);
    }

    @Override
    @WebMethod
    public ArrayOfSpecialization getArrayOfSpecializationExtend(Integer id)  //получение списка специальностей
    {
        ArrayOfSpecialization arr = new ArrayOfSpecialization();
        List<Specialization> specializations = arr.getSpecializations();
        // Запрос на вывод специальности

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectSpecialization(id));
        //Specialization spec = new Specialization();
        try {
            while(ResultSQLRequest.next())
            {
                Specialization spec = new Specialization();
                spec.setId((ResultSQLRequest.getInt("id")));
                spec.setName((ResultSQLRequest.getString("name")));
                specializations.add(spec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setSpecializations(specializations);
        /*-----LogWriter*/
        String params=getParam("Integer idLpu",id.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /*-----LogWriter*/
        return arr;
    }


    //TODO
    @Override
    @WebMethod
    public ArrayOfDoctor getArrayOfDoctor(Integer id_specialization)//получение списка врачей
    {
       return getArrayOfDoctorExtend(id_specialization,180);
    }

    @Override
    @WebMethod
    public ArrayOfDoctor getArrayOfDoctorExtend(Integer id_specialization, Integer codeId){//получение списка врачей
        ArrayOfDoctor arr = new ArrayOfDoctor();
        List<Doctor> doctors = arr.getDoctors();
        //Запрос на вывод врачей
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDoctor(id_specialization,codeId));

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setDoctors(doctors);


        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }


    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesFirst(Integer id_specialization) {
       return getArrayOfDatesFirstExtend(id_specialization,180);
    }

    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesFirstExtend(Integer id_specialization,Integer codeId) {
        ArrayOfDate arr = new ArrayOfDate();
        List<Date> dates = arr.getDates();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateFirst(id_specialization,codeId));
        Date dat = new Date();

        try {
            while(ResultSQLRequest.next())
            {
                dat = new Date();
                dat.setId((ResultSQLRequest.getInt("id")));
                dat.setDate((ResultSQLRequest.getString("date")));
                dates.add(dat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setDates(dates);

        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }
    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) {
       return getArrayOfDatesSecondExtend(id_specialization,id_doctor,180);
    }

    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesSecondExtend(Integer id_specialization, Integer id_doctor,Integer codeId) {
        ArrayOfDate arr = new ArrayOfDate();
        List<Date> dates = arr.getDates();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateSecond(id_doctor,id_specialization,codeId));
        Date dat = new Date();

        try {
            while(ResultSQLRequest.next())
            {
                dat = new Date();
                dat.setId((ResultSQLRequest.getInt("id")));
                dat.setDate((ResultSQLRequest.getString("date")));
                dates.add(dat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        arr.setDates(dates);

        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        params+=getParam("Integer id_doctor",id_doctor.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/
        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_date)  {
        return getArrayOfTimesFirstExtend(id_specialization,id_date,180);
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesFirstExtend(Integer id_specialization, Integer id_date, Integer codeId)  {
        ArrayOfTime arr = new ArrayOfTime();
        List<Time> times = arr.getTimes();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeFirst(id_specialization, id_date,codeId));
        Time tim = new Time();

        try {
            while(ResultSQLRequest.next())
            {
                tim = new Time();
                tim.setId((ResultSQLRequest.getInt("id")));
                tim.setTime((ResultSQLRequest.getString("time")));
                times.add(tim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setTimes(times);

        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        params+=getParam("Integer id_date",id_date.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesSecond(Integer id_date)  {
       return getArrayOfTimesSecondExtend(id_date,180);
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesSecondExtend(Integer id_date ,Integer codeId)  {
        ArrayOfTime arr = new ArrayOfTime();
        List<Time> times = arr.getTimes();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeSecond(id_date,codeId));

        Time tim = new Time();
        try {
            while(ResultSQLRequest.next())
            {
                tim = new Time();
                tim.setId((ResultSQLRequest.getInt("id")));
                tim.setTime((ResultSQLRequest.getString("time")));
                times.add(tim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setTimes(times);

        /*-----LogWriter
        String params=getParam("Integer id_date",id_date.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        *-----LogWriter*/

        return arr;
    }

    /*private static void getTimes(Integer id_specialization, Integer id_date)
    {
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeFirst(id_specialization, id_date));
        Time tim = new Time();
    }
    private static ArrayOfTime getTimes(Integer id_date)
    {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        arr.setTimes(times);

        *//**-----LogWriter*//*
        String params=getParam("Integer id_date",id_date.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        *//**-----LogWriter*//*

        return arr;
    }*/
    @Override
    @WebMethod
    public ArrayOfAllinformation getArrayOfAllinformation(Integer id_time) {
      //return getArrayOfAllinformationExtend(id_time,180);
        ArrayOfAllinformation arrayOfAllinformation = new ArrayOfAllinformation();
        List<AllInformation> allInformations = arrayOfAllinformation.getAllinformation();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectAllInformation(id_time));
        String SQL_res="";

        AllInformation allInformation = new AllInformation();
        try {
            while(ResultSQLRequest.next())
            {
                allInformation = new AllInformation();
                allInformation.setName((ResultSQLRequest.getString("name")));
                allInformation.setLastname((ResultSQLRequest.getString("lastname")));
                allInformation.setFirstname((ResultSQLRequest.getString("firstname")));
                allInformation.setMiddlename((ResultSQLRequest.getString("middlename")));
                allInformation.setDate((ResultSQLRequest.getString("date")));
                allInformation.setTime((ResultSQLRequest.getString("time")));
                allInformations.add(allInformation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        arrayOfAllinformation.setAllinfomation(allInformations);

       /* *//**//**-----LogWriter*//**//*
        String params=getParam("Integer id_time",id_time.toString());
        writeLog(Log(getMethodName(),params,arrayOfAllinformation+""));
        *//**//**-----LogWriter*//**//**/


        return arrayOfAllinformation;
    }

   /* @Override
    @WebMethod
    public ArrayOfAllinformation getArrayOfAllinformationExtend(Integer id_time ,Integer codeId) {
        ArrayOfAllinformation arrayOfAllinformation = new ArrayOfAllinformation();
        List<AllInformation> allInformations = arrayOfAllinformation.getAllinformation();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectAllInformation(id_time,codeId));
        String SQL_res="";

        AllInformation allInformation = new AllInformation();
        try {
            while(ResultSQLRequest.next())
            {
                allInformation = new AllInformation();
                allInformation.setName((ResultSQLRequest.getString("name")));
                allInformation.setLastname((ResultSQLRequest.getString("lastname")));
                allInformation.setFirstname((ResultSQLRequest.getString("firstname")));
                allInformation.setMiddlename((ResultSQLRequest.getString("middlename")));
                allInformation.setDate((ResultSQLRequest.getString("date")));
                allInformation.setTime((ResultSQLRequest.getString("time")));
                allInformations.add(allInformation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        arrayOfAllinformation.setAllinfomation(allInformations);

        *//**-----LogWriter*//*
        String params=getParam("Integer id_time",id_time.toString());
        writeLog(Log(getMethodName(),params,arrayOfAllinformation+""));
        *//**-----LogWriter*//*


        return arrayOfAllinformation;
    }*/
    @Override
    @WebMethod
    public ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time) {

        //System.out.println(CheckTimeCalendar(id_time));
        try {
            if(CheckTimeCalendar(id_time)==0)
            {
                ResultSet res = sql_connect.SQL_Select(Sql_request.UpdatePatient(id_patient,id_time));
                int res_str = 0;

                while (res.next()){

                    res_str = res.getInt("id");

                }


                if (res_str > 0) {
                    /**-----LogWriter*/
                    String params=getParam("Integer id_patient",id_patient.toString());
                    params+=getParam("Integer id_time",id_time.toString());
                    writeLog(Log(getMethodName(),params,res_str+""));
                    /**-----LogWriter*/
                    return getArrayOfAllinformation(id_time);
                } else {return SetNullPatient();}
            }else return SetNullPatient();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  SetNullPatient();
    }


    private ArrayOfAllinformation SetNullPatient()
    {
        ArrayOfAllinformation arrayOfAllinformation = new ArrayOfAllinformation();
        List<AllInformation> allInformations = arrayOfAllinformation.getAllinformation();

        AllInformation allInformation = new AllInformation();

        allInformation = new AllInformation();
        allInformation.setName("0");
        allInformation.setLastname("0");
        allInformation.setFirstname("0");
        allInformation.setMiddlename("0");
        allInformation.setDate("0");
        allInformation.setTime("0");
        allInformations.add(allInformation);
        arrayOfAllinformation.setAllinfomation(allInformations);
        return arrayOfAllinformation;
    }


    @Override
    @WebMethod
    public void TEST(String what){
        System.out.println(what);
    }

    @Override
    @WebMethod
    public ArrayOfAllinformation setUnknownPatientRecord(String FIO, String birthday,Integer id_time, String secretKey, String ip, String phone)
    {
        System.out.println("ФИО:"+FIO);
        System.out.println("BD: "+birthday);
        System.out.println("ID:  "+id_time);
        System.out.println("Secrt: "+secretKey);
        System.out.println("IP: "+ip);
        System.out.println("Phone: "+phone);

        if(secretKey.equals(WebServPublisher.key)) {

           String[] ss = birthday.split("\\.");
            String bd = ss[2] + "-" + ss[1] + "-" + ss[0];

            //System.out.println(birthday);
            ss = FIO.split(" ");
            String lastname = ss[0].toUpperCase();
            String firstname = ss[1].toUpperCase();
            String middlename = ss[2].toUpperCase();

            //System.out.println(lastname+" "+firstname+" "+middlename);

            ResultSet res = sql_connect.SQL_Select(Sql_request.SelectUnknownPatient(lastname, firstname, middlename, bd));
            Integer res_str=0;
            int i = 0;
            try {
                while (res.next()) {
                    res_str = res.getInt("id");
                    i++;
                   // System.out.println("ID="+res_str);
                }
                //System.out.println(i);
                if (i == 1) {
                    res = sql_connect.SQL_Select(Sql_request.RecordKnownPatient(res_str,phone, id_time));
                } else {
                    res = sql_connect.SQL_Select(Sql_request.RecordUnknownPatient(FIO, birthday, phone, id_time));
                }

                /**-----LogWriter*/
                String params=getParam("String FIO",FIO.toString());
                params+=getParam("Integer id_time",id_time.toString());
                params+=getParam("String birthday",birthday.toString());
                params+=getParam("String ip",ip.toString());
                writeLog(Log(getMethodName(),params,res_str+""));
                /**-----LogWriter*/
                return getArrayOfAllinformation(id_time);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            return SetNullPatient();
        }

        return SetNullPatient();
    }

    public int CheckTimeCalendar(Integer id_time){
        ResultSet res = sql_connect.SQL_Select(Sql_request.CheckPatientEmpty(id_time));
        int res_str=0;
        try {
            while(res.next()){
                res_str = res.getInt("prepatient_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**LogWriter*/
        String params=getParam("Integer id_time",id_time.toString());
        writeLog(Log(getMethodName(),params,res_str+""));
        return res_str;
    }



    private static void writeLog(String string)
    {
        if(!WebServPublisher.logging.equals("0")) {
            try (FileWriter writer = new FileWriter(WebServPublisher.pathSave + "/Log.txt", true)) {
                writer.write(string);
                writer.close();
            } catch (IOException ex) {System.out.println(ex.getMessage());}
        }
    }

    public static String Log(String methodName, String params, String returns)
    {
        String res="<log>";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        res+="<ID>"+tempid+"</ID>";
        res+="<Date>"+dateFormat.format(date)+"</Date>";
        res+=methodName;
        res+=params;
        res+="<return>"+returns+"</return>";
        res+="</method></log>";
        return res;
    }

    public static String getParam(String param,String value)
    {
        String res = "<param>"+param+"</param><value>"+value+"</value>";
        return res;
    }
    public static String getMethodName()
    {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        if (trace.length > 1)
        {
            StackTraceElement element = trace[1];
            //System.out.format("[%s] %s", element.getMethodName(), message);
            return "<method>"+element.getMethodName();
        }
        return "";
    }

    private static Boolean CheckInput(String str)
    {
        if ((str.matches("^\\d+$"))) {
            return true;
        }
        return false;
    }
}






