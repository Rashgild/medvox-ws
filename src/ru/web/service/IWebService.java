package ru.web.service;

import ru.web.classes.*;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

/**
 * Created by rkurbanov on 01.08.16.
 */
@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWebService {

    // говорим, что этот метод можно вызывать удаленно
    @WebMethod
    ArrayOfPatient getArrayOfPatient(Integer birthday)  ;
    ArrayOfSpecialization getArrayOfSpecialization();
    ArrayOfSpecialization getArrayOfSpecializationExtend(Integer id);
    ArrayOfDoctor getArrayOfDoctor(Integer id_specialization);
    ArrayOfDate getArrayOfDatesFirst(Integer id_specialization);
    ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor);
    ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_day);
    ArrayOfTime getArrayOfTimesSecond(Integer id_day);
    ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time);
    //ArrayOfAllinformation setUnknownPatientRecord(String FIO, String birthday,Integer id_time, String secretKey, String ip);
    ArrayOfAllinformation setUnknownPatientRecord(String FIO, String birthday,Integer id_time, String secretKey, String ip, String phone);
    int CheckTimeCalendar(Integer id_time);
    ArrayOfAllinformation getArrayOfAllinformation(Integer id_time);

    void TEST(String what);


    ArrayOfDoctor getArrayOfDoctorExtend(Integer id_specialization, Integer codeId);
    ArrayOfDate getArrayOfDatesFirstExtend(Integer id_specialization, Integer codeId);
    ArrayOfDate getArrayOfDatesSecondExtend(Integer id_specialization, Integer id_doctor,Integer codeId);
    ArrayOfTime getArrayOfTimesFirstExtend(Integer id_specialization, Integer id_day,Integer codeId);
    ArrayOfTime getArrayOfTimesSecondExtend(Integer id_day, Integer codeId);
    //ArrayOfAllinformation getArrayOfAllinformationExtend(Integer id_time ,Integer codeId);
    //public
}








