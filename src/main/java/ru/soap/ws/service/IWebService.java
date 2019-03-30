package ru.soap.ws.service;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

import ru.soap.ws.entity.AllInformationList;
import ru.soap.ws.entity.Dates;
import ru.soap.ws.entity.Doctors;
import ru.soap.ws.entity.Patients;
import ru.soap.ws.entity.Specializations;
import ru.soap.ws.entity.Times;

@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWebService {

    @WebMethod
    Patients getArrayOfPatient(Integer birthday);

    Specializations getArrayOfSpecialization();

    Specializations getArrayOfSpecializationExtend(Integer id);

    Doctors getArrayOfDoctor(Integer id_specialization);

    Dates getArrayOfDatesFirst(Integer id_specialization);

    Dates getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor);

    Times getArrayOfTimesFirst(Integer id_specialization, Integer id_day);

    Times getArrayOfTimesSecond(Integer id_day);

    AllInformationList setPatientRecord(Integer id_patient, Integer id_time);

    AllInformationList setUnknownPatientRecord(String FIO, String birthday, Integer id_time, String secretKey, String ip, String phone);

    int CheckTimeCalendar(Integer id_time);

    AllInformationList getArrayOfAllinformation(Integer id_time);

    Doctors getArrayOfDoctorExtend(Integer id_specialization, Integer codeId);

    Dates getArrayOfDatesFirstExtend(Integer id_specialization, Integer codeId);

    Dates getArrayOfDatesSecondExtend(Integer id_specialization, Integer id_doctor, Integer codeId);

    Times getArrayOfTimesFirstExtend(Integer id_specialization, Integer id_day, Integer codeId);

    Times getArrayOfTimesSecondExtend(Integer id_day, Integer codeId);
}








