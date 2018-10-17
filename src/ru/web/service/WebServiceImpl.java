package ru.web.service;

import ru.web.SQL.Sql_request;
import ru.web.SQL.sql_connect;
import ru.web.classes.*;
import ru.web.endpoint.WebServPublisher;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@WebService(endpointInterface = "ru.web.service.IWebService")
public class WebServiceImpl implements IWebService {


    public static int tempid=1;
    @Override
    @WebMethod
    public ArrayOfPatient getArrayOfPatient(Integer birthday) throws IOException, SQLException //получение списка специальностей
    {
        tempid = birthday;
        ArrayOfPatient arr = new ArrayOfPatient();
        List<Patient> patients = arr.getPatients();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectPatients(birthday));
        Patient patient ;

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

        arr.setPatients(patients);

        /**-----LogWriter*/
        String params=getParam("Integer birthday",birthday.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfSpecialization getArrayOfSpecialization() throws SQLException {//получение списка специальностей
        ArrayOfSpecialization arr = new ArrayOfSpecialization();
        List<Specialization> specializations = arr.getSpecializations();
        // Запрос на вывод специальности
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectSpecialization());
        Specialization spec ;
            while(ResultSQLRequest.next()) {
               spec = new Specialization();
               spec.setId((ResultSQLRequest.getInt("id")));
               spec.setName((ResultSQLRequest.getString("name")));
               specializations.add(spec);
            }
        arr.setSpecializations(specializations);
        /**-----LogWriter*/
        String params=getParam("","");
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/
        return arr;
    }

//TODO
    @Override
    @WebMethod
    public ArrayOfDoctor getArrayOfDoctor(Integer id_specialization) throws SQLException {//получение списка врачей

        ArrayOfDoctor arr = new ArrayOfDoctor();
        List<Doctor> doctors = arr.getDoctors();
        //Запрос на вывод врачей
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDoctor(id_specialization));

        Doctor doc ;
            while(ResultSQLRequest.next()) {
                doc = new Doctor();
                doc.setId((ResultSQLRequest.getInt("id")));
                doc.setName((ResultSQLRequest.getString("name")));
                doc.setLastname((ResultSQLRequest.getString("lastname")));
                doc.setFirstname((ResultSQLRequest.getString("firstname")));
                doc.setMiddlename((ResultSQLRequest.getString("middlename")));
                doctors.add(doc);
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
    public ArrayOfDate getArrayOfDatesFirst(Integer id_specialization) throws SQLException {
        ArrayOfDate arr = new ArrayOfDate();

        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateFirst(id_specialization));
        List<Date> dates = getArrayOfDates(ResultSQLRequest);
        arr.setDates(dates);

        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }

    @Override
    @WebMethod
    public ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) throws SQLException {
        ArrayOfDate arr = new ArrayOfDate();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectDateSecond(id_doctor,id_specialization));
        List<Date> dates = getArrayOfDates(ResultSQLRequest);
        arr.setDates(dates);

        /**-----LogWriter*/
        String params=getParam("Integer id_specialization",id_specialization.toString());
        params+=getParam("Integer id_doctor",id_doctor.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }

    /**Получаем список дат из запроса*/
    private List<Date> getArrayOfDates(ResultSet aResultSet) throws SQLException {
        Date dat ;
        List<Date> dates = new ArrayList<>();

        while(aResultSet.next()) {
            dat = new Date();
            dat.setId((aResultSet.getInt("id")));
            dat.setDate((aResultSet.getString("date")));
            dates.add(dat);
        }
        return dates;
    }

    @Override
    @WebMethod
    public ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_date) throws SQLException {
        ArrayOfTime arr = new ArrayOfTime();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeFirst(id_specialization, id_date));
        List<Time> times = getArrayOfTimes(ResultSQLRequest);
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
    public ArrayOfTime getArrayOfTimesSecond(Integer id_date) throws SQLException {
        ArrayOfTime arr = new ArrayOfTime();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectTimeSecond(id_date));
        List<Time> times = getArrayOfTimes(ResultSQLRequest);
        arr.setTimes(times);

        /**-----LogWriter*/
        String params=getParam("Integer id_date",id_date.toString());
        writeLog(Log(getMethodName(),params,arr+""));
        /**-----LogWriter*/

        return arr;
    }

    /**Получаем список времен из запроса*/
    private List<Time> getArrayOfTimes(ResultSet aResultSet) throws SQLException {
        Date dat ;
        List<Time> times = new ArrayList<>();
        Time time = new Time();
        while(aResultSet.next()) {
            time = new Time();
            time.setId((aResultSet.getInt("id")));
            time.setTime((aResultSet.getString("time")));
            times.add(time);
        }
        return times;
    }

    @Override
    @WebMethod
    public ArrayOfAllinformation getArrayOfAllinformation(Integer id_time) throws SQLException {
        ArrayOfAllinformation arrayOfAllinformation = new ArrayOfAllinformation();
        List<AllInformation> allInformations = arrayOfAllinformation.getAllinformation();
        ResultSet ResultSQLRequest = sql_connect.SQL_Select(Sql_request.SelectAllInformation(id_time));

        AllInformation allInformation;
            while(ResultSQLRequest.next()) {
                allInformation = new AllInformation();
                allInformation.setName((ResultSQLRequest.getString("name")));
                allInformation.setLastname((ResultSQLRequest.getString("lastname")));
                allInformation.setFirstname((ResultSQLRequest.getString("firstname")));
                allInformation.setMiddlename((ResultSQLRequest.getString("middlename")));
                allInformation.setDate((ResultSQLRequest.getString("date")));
                allInformation.setTime((ResultSQLRequest.getString("time")));
                allInformations.add(allInformation);
            }


        arrayOfAllinformation.setAllinfomation(allInformations);

        /**-----LogWriter*/
        String params=getParam("Integer id_time",id_time.toString());
        writeLog(Log(getMethodName(),params,arrayOfAllinformation+""));
        /**-----LogWriter*/
        return arrayOfAllinformation;
    }

    @Override
    @WebMethod
    public ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time) throws IOException, SQLException {
        //System.out.println(CheckTimeCalendar(id_time));
        if(CheckTimeCalendar(id_time)==0) {
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
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int CheckTimeCalendar(Integer id_time) throws SQLException {
        ResultSet res = sql_connect.SQL_Select(Sql_request.CheckPatientEmpty(id_time));
        int res_str=0;
            while(res.next()){
                res_str = res.getInt("prepatient_id");
            }
      /**LogWriter*/
      String params=getParam("Integer id_time",id_time.toString());
      writeLog(Log(getMethodName(),params,res_str+""));
      return res_str;
    }

    private static void writeLog(String string) {
        if(!WebServPublisher.Log.equals("0")) {
            try (FileWriter writer = new FileWriter(WebServPublisher.PathSaveLog + "/Log.txt", true)) {
                writer.write(string);
                writer.close();
            } catch (IOException ex) {System.out.println(ex.getMessage());}
        }
    }

    public static String Log(String methodName, String params, String returns) {
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

    public static String getParam(String param,String value) {
        String res = "<param>"+param+"</param><value>"+value+"</value>";
        return res;
    }
    public static String getMethodName() {
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

    private static Boolean CheckInput(String str) {
        return (str.matches("^\\d+$"));
    }
}






