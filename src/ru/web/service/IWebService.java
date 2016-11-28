package ru.web.service;

import ru.web.classes.*;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

/**
 * Created by rkurbanov on 01.08.16.
 */
@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWebService {

    // говорим, что этот метод можно вызывать удаленно
    @WebMethod

    public ArrayOfPatient getArrayOfPatient(Integer birthday) throws IOException;
    public ArrayOfSpecialization getArrayOfSpecialization() throws IOException;
    public ArrayOfDoctor getArrayOfDoctor(Integer id_specialization) throws IOException;
    public ArrayOfDate getArrayOfDatesFirst(Integer id_specialization) throws IOException;
    public ArrayOfDate getArrayOfDatesSecond(Integer id_specialization, Integer id_doctor) throws IOException;
    public ArrayOfTime getArrayOfTimesFirst(Integer id_specialization, Integer id_day) throws IOException;
    public ArrayOfTime getArrayOfTimesSecond(Integer id_day) throws IOException;
   // public String getAllInformation(Integer id_time) throws IOException;
    public ArrayOfAllinformation setPatientRecord(Integer id_patient, Integer id_time) throws IOException;
    public int CheckTimeCalendar(Integer id_time) throws IOException;
    public ArrayOfAllinformation getArrayOfAllinformation(Integer id_time) throws IOException;

    //public
}








