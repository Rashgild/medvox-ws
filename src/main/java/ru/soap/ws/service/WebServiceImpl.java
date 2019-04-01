package ru.soap.ws.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import ru.soap.ws.entity.AllInformation;
import ru.soap.ws.entity.AllInformationList;
import ru.soap.ws.entity.Date;
import ru.soap.ws.entity.Dates;
import ru.soap.ws.entity.Doctor;
import ru.soap.ws.entity.Doctors;
import ru.soap.ws.entity.Patient;
import ru.soap.ws.entity.Patients;
import ru.soap.ws.entity.Specialization;
import ru.soap.ws.entity.Specializations;
import ru.soap.ws.entity.Time;
import ru.soap.ws.entity.Times;
import ru.soap.ws.utils.PropertiesReader;
import ru.soap.ws.utils.SqlConnect;
import ru.soap.ws.utils.SqlRequest;


@WebService(endpointInterface = "ru.soap.ws.service.IWebService")
public class WebServiceImpl implements IWebService {

    @Override
    @WebMethod
    public Patients getArrayOfPatient(Integer birthday) { //получение списка специальностей
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
        return arr;
    }

    @Override
    @WebMethod
    public Doctors getArrayOfDoctor(Integer id_specialization) {//получение списка врачей
        return getArrayOfDoctorExtend(id_specialization, 180);
    }

    @Override
    @WebMethod
    public Doctors getArrayOfDoctorExtend(Integer id_specialization, Integer codeId) {//получение списка врачей
        Doctors doctors = new Doctors();
        List<Doctor> doctorList = doctors.getDoctors();

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
                doctorList.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        doctors.setDoctors(doctorList);

        return doctors;
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
        Dates dates = new Dates();
        ResultSet ResultSQLRequest = SqlConnect.SQL_Select(SqlRequest.selectDateSecond(id_doctor, id_specialization, codeId));
        List<Date> dateList = getArrayOfDates(ResultSQLRequest);
        dates.setDates(dateList);

        return dates;
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
            e.printStackTrace();
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
        ResultSet resultSet = SqlConnect.SQL_Select(SqlRequest.selectAllInformation(id_time));

        AllInformation allInformation;
        try {
            while (resultSet.next()) {
                allInformation = new AllInformation();
                allInformation.setName((resultSet.getString("name")));
                allInformation.setLastname((resultSet.getString("lastname")));
                allInformation.setFirstname((resultSet.getString("firstname")));
                allInformation.setMiddlename((resultSet.getString("middlename")));
                allInformation.setDate((resultSet.getString("date")));
                allInformation.setTime((resultSet.getString("time")));
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
            if (checkTimeCalendar(id_time) == 0) {
                ResultSet resultSet = SqlConnect.SQL_Select(SqlRequest.updatePatient(id_patient, id_time));
                int res_str = 0;

                while (resultSet.next()) {
                    res_str = resultSet.getInt("id");
                }

                if (res_str > 0) {
                    return getArrayOfAllinformation(id_time);
                } else {
                    return SetNullPatient();
                }
            } else {
                return SetNullPatient();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SetNullPatient();
    }


    private AllInformationList SetNullPatient() {
        AllInformation allInformation = new AllInformation();
        allInformation.setName("0");
        allInformation.setLastname("0");
        allInformation.setFirstname("0");
        allInformation.setMiddlename("0");
        allInformation.setDate("0");
        allInformation.setTime("0");

        List<AllInformation> allInformations = new ArrayList<>();
        allInformations.add(allInformation);

        AllInformationList arrayOfAllinformation = new AllInformationList();
        arrayOfAllinformation.setAllinfomation(allInformations);

        return arrayOfAllinformation;
    }

    @Override
    @WebMethod
    public AllInformationList setUnknownPatientRecord(String FIO, String birthday, Integer id_time,
                                                      String secretKey, String ip, String phone) {

        PropertiesReader prop = new PropertiesReader();
        String key = prop.properties.getProperty("key.key");

        if (secretKey.equals(key)) {
            String[] ss = birthday.split("\\.");
            String bd = ss[2] + "-" + ss[1] + "-" + ss[0];

            ss = FIO.split(" ");
            String lastname = ss[0].toUpperCase();
            String firstname = ss[1].toUpperCase();
            String middlename = ss[2].toUpperCase();

            ResultSet resultSet = SqlConnect.SQL_Select(SqlRequest.selectUnknownPatient(lastname, firstname, middlename, bd));
            int res_str = 0;
            int i = 0;
            try {
                while (resultSet.next()) {
                    res_str = resultSet.getInt("id");
                    i++;
                }
                if (isTimeFree(id_time)) {

                    if (i == 1) {
                        resultSet = SqlConnect.SQL_Select(SqlRequest.recordKnownPatient(res_str, phone, id_time));
                    } else {
                        resultSet = SqlConnect.SQL_Select(SqlRequest.recordUnknownPatient(FIO, birthday, phone, id_time));
                    }

                    while (resultSet.next()) {
                        res_str = resultSet.getInt("id");
                    }
                    System.out.println(">>>>>" + res_str);

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

    private boolean isTimeFree(Integer id_time) {
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
        return prepatientId == 0 && prepatientInfo == null;
    }

    public int checkTimeCalendar(Integer id_time) {
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
}
