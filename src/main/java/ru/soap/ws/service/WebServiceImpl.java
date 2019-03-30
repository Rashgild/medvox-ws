package ru.soap.ws.service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;

import ru.soap.ws.entity.AllInformationList;
import ru.soap.ws.utils.SqlRequest;
import ru.soap.ws.utils.SqlConnect;
import ru.soap.ws.endpoint.WebService;
import ru.soap.ws.entity.AllInformation;
import ru.soap.ws.entity.Dates;
import ru.soap.ws.entity.Doctors;
import ru.soap.ws.entity.Patients;
import ru.soap.ws.entity.Specializations;
import ru.soap.ws.entity.Times;
import ru.soap.ws.entity.Date;
import ru.soap.ws.entity.Doctor;
import ru.soap.ws.entity.Patient;
import ru.soap.ws.entity.Specialization;
import ru.soap.ws.entity.Time;


@javax.jws.WebService(endpointInterface = "IWebService")
public class WebServiceImpl implements IWebService {

    private static int tempid = 1;

    @Override
    @WebMethod
    public Patients getArrayOfPatient(Integer birthday) { //получение списка специальностей
        tempid = birthday;
        Patients arrayOfPatient = new Patients();
        List<Patient> patients = arrayOfPatient.getPatients();

        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectPatients(birthday));
        Patient patient;

        try {
            while (ResultSQLRequest.next()) {
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

        arrayOfPatient.setPatients(patients);

        /**-----LogWriter*/
        String params = getParam("Integer birthday", birthday.toString());
        writeLog(Log(getMethodName(), params, arrayOfPatient + ""));
        /**-----LogWriter*/

        return arrayOfPatient;
    }


    @Override
    @WebMethod
    public Specializations getArrayOfSpecialization() { //получение списка специальностей
        return getArrayOfSpecializationExtend(180);
    }

    @Override
    @WebMethod
    public Specializations getArrayOfSpecializationExtend(Integer id) {  //получение списка специальностей
        Specializations arr = new Specializations();
        List<Specialization> specializations = arr.getSpecializations();
        // Запрос на вывод специальности

        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectSpecialization(id));
        //Specialization spec = new Specialization();
        try {
            while (ResultSQLRequest.next()) {
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
        String params = getParam("Integer idLpu", id.toString());
        writeLog(Log(getMethodName(), params, arr + ""));
        /*-----LogWriter*/
        return arr;
    }

    @Override
    @WebMethod
    public Doctors getArrayOfDoctor(Integer id_specialization)//получение списка врачей
    {
        return getArrayOfDoctorExtend(id_specialization, 180);
    }

    @Override
    @WebMethod
    public Doctors getArrayOfDoctorExtend(Integer id_specialization, Integer codeId) {//получение списка врачей
        Doctors arr = new Doctors();
        List<Doctor> doctors = arr.getDoctors();
        //Запрос на вывод врачей
        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectDoctor(id_specialization, codeId));

        Doctor doc;
        try {
            while (ResultSQLRequest.next()) {
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
        String params = getParam("Integer id_specialization", id_specialization.toString());
        writeLog(Log(getMethodName(), params, arr + ""));
        /**-----LogWriter*/

        return arr;
    }


    @Override
    @WebMethod
    public Dates getArrayOfDatesFirst(Integer id_specialization) {
        return getArrayOfDatesFirstExtend(id_specialization, 180);
    }

    @Override
    @WebMethod
    public Dates getArrayOfDatesFirstExtend(Integer id_specialization, Integer codeId) {
        Dates arr = new Dates();
        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectDateFirst(id_specialization, codeId));
        List<Date> dates = getArrayOfDates(ResultSQLRequest);
        arr.setDates(dates);

        /**-----LogWriter*/
        String params = getParam("Integer id_specialization", id_specialization.toString());
        writeLog(Log(getMethodName(), params, arr + ""));
        /**-----LogWriter*/

        return arr;
    }

    @Override
    @WebMethod
    public Dates getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) {
        return getArrayOfDatesSecondExtend(id_specialization, id_doctor, 180);
    }

    @Override
    @WebMethod
    public Dates getArrayOfDatesSecondExtend(Integer id_specialization, Integer id_doctor, Integer codeId) {
        Dates arr = new Dates();

        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectDateSecond(id_doctor, id_specialization, codeId));
        List<Date> dates = getArrayOfDates(ResultSQLRequest);
        arr.setDates(dates);

        /**-----LogWriter*/
        String params = getParam("Integer id_specialization", id_specialization.toString());
        params += getParam("Integer id_doctor", id_doctor.toString());
        writeLog(Log(getMethodName(), params, arr + ""));
        /**-----LogWriter*/
        return arr;
    }

    /**
     * Получаем список дат из запроса
     */
    private List<Date> getArrayOfDates(ResultSet aResultSet) {
        Date dat;
        List<Date> dates = new ArrayList<>();
        try {
            while (aResultSet.next()) {
                dat = new Date();
                dat.setId((aResultSet.getInt("id")));
                dat.setDate((aResultSet.getString("date")));
                dates.add(dat);
            }
        } catch (SQLException e) {
        }
        return dates;
    }

    @Override
    @WebMethod
    public Times getArrayOfTimesFirst(Integer id_specialization, Integer id_date) {
        return getArrayOfTimesFirstExtend(id_specialization, id_date, 180);
    }

    @Override
    @WebMethod
    public Times getArrayOfTimesFirstExtend(Integer id_specialization, Integer id_date, Integer codeId) {
        Times arr = new Times();

        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectTimeFirst(id_specialization, id_date, codeId));
        List<Time> times = getArrayOfTimes(ResultSQLRequest);
        arr.setTimes(times);

        /**-----LogWriter*/
        String params = getParam("Integer id_specialization", id_specialization.toString());
        params += getParam("Integer id_date", id_date.toString());
        writeLog(Log(getMethodName(), params, arr + ""));
        /**-----LogWriter*/

        return arr;
    }

    @Override
    @WebMethod
    public Times getArrayOfTimesSecond(Integer id_date) {
        return getArrayOfTimesSecondExtend(id_date, 180);
    }

    @Override
    @WebMethod
    public Times getArrayOfTimesSecondExtend(Integer id_date, Integer codeId) {
        Times arr = new Times();
        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectTimeSecond(id_date, codeId));
        List<Time> times = getArrayOfTimes(ResultSQLRequest);
        arr.setTimes(times);

        return arr;
    }

    /**
     * Получаем список времен из запроса
     */
    private List<Time> getArrayOfTimes(ResultSet aResultSet) {
        List<Time> times = new ArrayList<>();
        Time time;
        try {
            while (aResultSet.next()) {
                time = new Time();
                time.setId((aResultSet.getInt("id")));
                time.setTime((aResultSet.getString("time")));
                times.add(time);
            }
        } catch (SQLException e) {
        }
        return times;
    }

    @Override
    @WebMethod
    public AllInformationList getArrayOfAllinformation(Integer id_time) {
        AllInformationList arrayOfAllinformation = new AllInformationList();
        List<AllInformation> allInformations = arrayOfAllinformation.getAllinformation();
        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectAllInformation(id_time));

        AllInformation allInformation;
        try {
            while (ResultSQLRequest.next()) {
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

        return arrayOfAllinformation;
    }

    @Override
    @WebMethod
    public AllInformationList setPatientRecord(Integer id_patient, Integer id_time) {
        try {
            if (CheckTimeCalendar(id_time) == 0) {
                ResultSet res = SqlConnect.SQL_Select(SqlRequest.updatePatient(id_patient, id_time));
                int res_str = 0;

                while (res.next()) {
                    res_str = res.getInt("id");
                }

                if (res_str > 0) {
                    /**-----LogWriter*/
                    String params = getParam("Integer id_patient", id_patient.toString());
                    params += getParam("Integer id_time", id_time.toString());
                    writeLog(Log(getMethodName(), params, res_str + ""));
                    /**-----LogWriter*/
                    return getArrayOfAllinformation(id_time);
                } else {
                    return SetNullPatient();
                }
            } else return SetNullPatient();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SetNullPatient();
    }


    private AllInformationList SetNullPatient() {
        AllInformationList arrayOfAllinformation = new AllInformationList();
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
    public AllInformationList setUnknownPatientRecord(String FIO, String birthday, Integer id_time, String secretKey, String ip, String phone) {
        System.out.println("ФИО:" + FIO);
        System.out.println("BD: " + birthday);
        System.out.println("ID:  " + id_time);
        System.out.println("Secrt: " + secretKey);
        System.out.println("IP: " + ip);
        System.out.println("Phone: " + phone);

        if (secretKey.equals(WebService.key)) {

            String[] ss = birthday.split("\\.");
            String bd = ss[2] + "-" + ss[1] + "-" + ss[0];


            ss = FIO.split(" ");
            String lastname = ss[0].toUpperCase();
            String firstname = ss[1].toUpperCase();
            String middlename = ss[2].toUpperCase();


            ResultSet res = SqlConnect.SQL_Select(SqlRequest.selectUnknownPatient(lastname, firstname, middlename, bd));
            Integer res_str = 0;
            int i = 0;
            try {
                while (res.next()) {
                    res_str = res.getInt("id");
                    i++;
                }
                //System.out.println(i);

                if (isTimeFree(id_time)) {

                    if (i == 1) {
                        res = SqlConnect.SQL_Select(SqlRequest.recordKnownPatient(res_str, phone, id_time));
                    } else {
                        res = SqlConnect.SQL_Select(SqlRequest.recordUnknownPatient(FIO, birthday, phone, id_time));
                    }

                    while (res.next()) {
                        res_str = res.getInt("id");
                    }
                    System.out.println(">>>>>" + res_str);


                    /**-----LogWriter*/
                    String params = getParam("String FIO", FIO.toString());
                    params += getParam("Integer id_time", id_time.toString());
                    params += getParam("String birthday", birthday.toString());
                    params += getParam("String ip", ip.toString());
                    writeLog(Log(getMethodName(), params, res_str + ""));
                    /**-----LogWriter*/

                    return getArrayOfAllinformation(id_time);
                } else {
                    return SetNullPatient();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return SetNullPatient();
        }

        return SetNullPatient();
    }

    public boolean isTimeFree(Integer id_time) {
        ResultSet res = SqlConnect.SQL_Select(SqlRequest.checkPatientEmpty(id_time));
        int prepatientId = 0;
        String prepatientInfo = "";
        try {
            while (res.next()) {
                prepatientId = res.getInt("prepatient_id");
                prepatientInfo = res.getString("prepatientinfo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("---->>>" + prepatientId);
        System.out.println("---->>>" + prepatientInfo);


        if (prepatientId == 0 && prepatientInfo == null) return true;
        else return false;
    }

    public int CheckTimeCalendar(Integer id_time) {

        ResultSet res = SqlConnect.SQL_Select(SqlRequest.checkPatientEmpty(id_time));
        int prepatientId = 0;

        try {
            while (res.next()) {
                prepatientId = res.getInt("prepatient_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prepatientId;
    }


    private static void writeLog(String string) {
        if (!WebService.logging.equals("0")) {
            try (FileWriter writer = new FileWriter(WebService.pathSave + "/Log.txt", true)) {
                writer.write(string);
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String Log(String methodName, String params, String returns) {
        String res = "<log>";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        res += "<ID>" + tempid + "</ID>";
        res += "<Date>" + dateFormat.format(date) + "</Date>";
        res += methodName;
        res += params;
        res += "<return>" + returns + "</return>";
        res += "</method></log>";
        return res;
    }

    public static String getParam(String param, String value) {
        String res = "<param>" + param + "</param><value>" + value + "</value>";
        return res;
    }

    public static String getMethodName() {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        if (trace.length > 1) {
            StackTraceElement element = trace[1];
            //System.out.format("[%s] %s", element.getMethodName(), message);
            return "<method>" + element.getMethodName();
        }
        return "";
    }

    private static Boolean CheckInput(String str) {
        return str.matches("^\\d+$");
    }
}






