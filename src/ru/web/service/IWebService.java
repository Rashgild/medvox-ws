package ru.web.service;

import ru.web.classes.*;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by rkurbanov on 01.08.16.
 */
@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWebService {

    // говорим, что этот метод можно вызывать удаленно
    @WebMethod

    public ArrayOfPatient getArrayOfPatient(Integer birthday) throws IOException, SQLException;
    public ArrayOfSpecialization getArrayOfSpecialization() throws IOException, SQLException;
    public ArrayOfDoctor getArrayOfDoctor(Integer id_specialization) throws IOException, SQLException;
    public ArrayOfDate getArrayOfDatesFirst(Integer id_specialization) throws IOException, SQLException;
    public ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) throws IOException, SQLException;
    public ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_day) throws IOException, SQLException;
    public ArrayOfTime getArrayOfTimesSecond(Integer id_day) throws IOException, SQLException;
    public ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time) throws IOException, SQLException;
    public int CheckTimeCalendar(Integer id_time) throws IOException, SQLException;
    public ArrayOfAllinformation getArrayOfAllinformation(Integer id_time) throws IOException, SQLException;

    //public
}








