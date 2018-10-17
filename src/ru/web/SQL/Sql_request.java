package ru.web.SQL;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rkurbanov on 04.08.16.
 */
public class Sql_request {


    private static String reserve_type="";
    private static String lpu_id = "";

    private static void LpuIdAndReserveType(Integer id){
        if(id==180){
            lpu_id = "= 180 ";
            reserve_type ="is null ";
        }

        if(id==246){
            lpu_id = "in(180,246)";
            reserve_type ="=3";
        }
    }

    public static String SelectSpecialization(Integer id){

        LpuIdAndReserveType(id);

        String req = "select vwf.id ,vwf.name\n" +
    "                from WorkFunction as wf \n" +
    "                left join Worker as w on w.id=wf.worker_id\n" +
    "                left join Patient as p on p.id=w.person_id\n" +
    "                inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id\n" +
    "                left join WorkCalendar wc on wc.workFunction_id=wf.id\n" +
    "                left join workcalendarday wcd on wcd.workcalendar_id=wc.id\n" +
    "                left join workcalendartime wct on wct.workCalendarDay_id=wcd.id\n" +
    "                left join mislpu mlGr on mlGr.id=wf.lpu_id\n" +
    "                where wc.id is not null\n " +
                "and (wcd.isDeleted is null or wcd.isDeleted = false) \n" +
                "and (wct.isDeleted is null or wct.isDeleted = false) " +
    "                and wcd.calendardate>=current_date\n" +
    "                and wf.group_id is null \n" +
    "                and (wf.archival is null or wf.archival='0')\n" +
    "                and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null)\n" +
    "                and coalesce(w.lpu_id,wf.lpu_id) "+lpu_id+" \n"+
    "                and wct.medCase_id is null and wct.prepatient_id is null \n" +
    "                and (wct.prepatientinfo is null or wct.prepatientinfo='') \n" +
    "                and wct.reserveType_id "+reserve_type+" \n"+
    "                group by vwf.id, vwf.name\n" +
    "                having count(wct.id)>0;";
        return req;

    }

    public  static String SelectDoctor(Integer id_spec, Integer codeId) {
        LpuIdAndReserveType(codeId);

        String req= "SELECT\n" +
                "wf.id , vwf.name, case when wf.dtype='PersonalWorkFunction' then p.lastname\n" +
                "else wf.groupname end as lastname,\n" +
                "coalesce(p.firstname,'') as firstname, coalesce(p.middlename,'') as middlename\n" +
                "from WorkFunction as wf\n" +
                "left join Worker as w on w.id=wf.worker_id\n" +
                "left join Patient as p on p.id=w.person_id\n" +
                "inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id\n" +
                "left join WorkCalendar wc on wc.workFunction_id=wf.id\n" +
                "left join mislpu mlGr on mlGr.id=wf.lpu_id\n" +
                "left join workcalendarday wcd on wcd.workcalendar_id=wc.id\n" +
                "left join workcalendartime wct on wct.workCalendarDay_id=wcd.id\n" +
                "where wc.id is not null\n" +
                " and (wcd.isDeleted is null or wcd.isDeleted='0')  and (wct.isDeleted is null or  wct.isDeleted='0')" +
                "and wf.group_id is null\n" +
                "and (wf.archival is null or wf.archival='0')\n" +
                "and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null)\n" +
                "and coalesce(wf.lpu_id,w.lpu_id) "+lpu_id+" \n" +
                "and wct.prepatient_id is null\n" +
                "and (wct.prepatientinfo is null or wct.prepatientinfo='') \n" +
                "and wct.reserveType_id "+reserve_type+" \n"+
                "and wct.medCase_id is null\n" +
                "and wct.createprerecord is null\n" +
                "and wcd.calendardate>=current_date\n" +
                "and vwf.id = " +id_spec+" \n"+
                "group by wf.id, vwf.name, p.lastname ,p.firstname,p.middlename\n" +
                "having count(wct.id)>0";

        System.out.println(req);
        return req;
    }

    /*public  static String SelectDoctor(Integer id_spec){
        String req= "SELECT" +
                "wf.id , vwf.name, case when wf.dtype='PersonalWorkFunction' then p.lastname" +
                "else wf.groupname end as lastname," +
                "coalesce(p.firstname,'') as firstname, coalesce(p.middlename,'') as middlename" +
                "from WorkFunction as wf" +
                "left join Worker as w on w.id=wf.worker_id" +
                "left join Patient as p on p.id=w.person_id" +
                "inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
                "left join WorkCalendar wc on wc.workFunction_id=wf.id" +
                "left join mislpu mlGr on mlGr.id=wf.lpu_id" +
                "left join workcalendarday wcd on wcd.workcalendar_id=wc.id" +
                "left join workcalendartime wct on wct.workCalendarDay_id=wcd.id" +
                "where wc.id is not null" +
                "and wf.group_id is null" +
                "and (wf.archival is null or wf.archival='0')" +
                "and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null)" +
                "and coalesce(wf.lpu_id,w.lpu_id)=180" +
                "and wct.prepatient_id is null" +
                "and (wct.prepatientinfo is null or wct.prepatientinfo='') " +
                "and wct.reserveType_id is null" +
                "and wct.medCase_id is null" +
                "and wct.createprerecord is null" +
                "and wcd.calendardate>=current_date" +
                "and vwf.id = " +id_spec+
                "group by wf.id, vwf.name, p.lastname ,p.firstname,p.middlename" +
                "having count(wct.id)>0";
        return req;
    }*/

    public  static  String SelectDateSecond(Integer id_doctor, Integer id_specialization, Integer codeId) {
        LpuIdAndReserveType(codeId);

        String req ="select  max(wcd.id) as id, to_char(wcd.calendardate,'dd.mm.yyyy') as date\n" +
                "from workCalendar wc \n" +
                "left join workcalendarday wcd on wcd.workcalendar_id=wc.id\n" +
                "left join workcalendartime wct on wct.workcalendarday_id=wcd.id\n" +
                "left join workfunction wf on wf.id=wc.workfunction_id\n" +
                "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
                "where \n" +
                "wcd.calendardate>=current_date\n" +
                "and (wcd.isDeleted is null or wcd.isDeleted ='0') and (wct.isDeleted is null or wct.isDeleted ='0') \n" +
                "and wct.medCase_id is null and wct.prepatient_id is null  \n" +
                "and (wct.prepatientinfo is null or wct.prepatientinfo='') \n" +
                "and wct.reserveType_id "+reserve_type+"\n" +
                "and wf.id= "+id_doctor+"\n" +  //поиск по сотруднику
                "and vwf.id= "+id_specialization+" \n" + //поиск по специализации
                "and case when wcd.calendardate=current_date then case when wct.timefrom > current_time then 1 else 0 end else 1 end =1\n" +
                "group by wcd.calendardate\n" +
                "having count (wct.id)>0\n" +
                "order by wcd.calendardate";
     return req;
    }

    public  static  String SelectTimeSecond(Integer id_date,Integer codeId) {
        LpuIdAndReserveType(codeId);
        /*String req = "select max(wct.id) as id,cast(wct.timeFrom as varchar(5)) as time\n" +
                "from WorkCalendarTime wct\n" +
                "left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id\n" +
                "left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id\n" +
                "left join workcalendar wc on wc.id=wcd.workcalendar_id\n" +
                "left join workfunction wf on wf.id=wc.workfunction_id\n" +
                "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
                "where \n" +
                "wct.workCalendarDay_id = \n" + id_date +
                " and wcd.isDeleted is null and wct.isDeleted is null " +
                "and wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') " +
                "and wct.reserveType_id "+reserve_type+"\n"+
                "and (wcd.calendardate>current_date or (wcd.calendardate=current_date and wct.timefrom> current_time)) \n" +
                "group by wct.timeFrom\n" +
                "order by wct.timeFrom";*/
        String req ="select max(wct.id) as id,cast(wct.timeFrom as varchar(5)) as time\n" +
                "from WorkCalendarTime wct\n" +
                "left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id\n" +
                "left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id\n" +
                "left join workcalendar wc on wc.id=wcd.workcalendar_id\n" +
                "left join workfunction wf on wf.id=wc.workfunction_id\n" +
                "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
                "where \n" +
                "wct.workCalendarDay_id =  "+id_date+"\n" +
                "and (wcd.isDeleted is null or wcd.isDeleted = '0') and (wct.isDeleted is null or wct.isDeleted = '0')\n" +
                "and wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='')\n" +
                "and wct.reserveType_id "+reserve_type+"\n" +
                "and (wcd.calendardate>current_date or (wcd.calendardate=current_date and wct.timefrom> current_time)) \n" +
                "group by wct.timeFrom\n" +
                "order by wct.timeFrom;";
        return  req;
    }

    public  static  String SelectAllInformation(Integer id_time)
    {
        String req = "select vwf.name, \n" +
                "case when wf.dtype ='GroupWorkFunction' then wf.groupname else p.lastname end\n" +
                ",coalesce (p.firstname,'') as \"firstname\"\n" +
                ",coalesce (p.middlename,'')as \"middlename\",to_char(wcd.calendardate,'dd.mm.yyyy') as date ,cast(wct.timeFrom as varchar(5)) as time\n" +
                "from WorkFunction as wf  \n" +
                "left join Worker as w on w.id=wf.worker_id \n" +
                "left join Patient as p on p.id=w.person_id \n" +
                "inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id \n" +
                "left join WorkCalendar wc on wc.workFunction_id=wf.id \n" +
                "left join mislpu mlGr on mlGr.id=wf.lpu_id \n" +
                "left join workcalendarday wcd on wcd.workcalendar_id=wc.id \n" +
                "left join workcalendartime wct on wct.workcalendarday_id=wcd.id \n" +
                "where wc.id is not null \n" +
                "and wf.group_id is null  \n" +
                "and (wf.archival is null or wf.archival='0') \n" +
                "and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null) \n" +
                "and wct.id ="+id_time;

        return  req;
    }

    public  static  String SelectDateFirst(Integer id_specialization,Integer codeId) {
        LpuIdAndReserveType(codeId);
       String req ="select  max(wcd.id) as id, to_char(wcd.calendardate,'dd.mm.yyyy') as date\n" +
               "                 from workCalendar wc \n" +
               "                 left join workcalendarday wcd on wcd.workcalendar_id=wc.id\n" +
               "                 left join workcalendartime wct on wct.workcalendarday_id=wcd.id\n" +
               "                 left join workfunction wf on wf.id=wc.workfunction_id\n" +
               "                left join worker w on w.id=wf.worker_id\n"+
               "                 left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
               "                 where \n" +
               "                 wcd.calendardate>=current_date\n" +
               "and (wcd.isDeleted is null or wcd.isDeleted = false)  \n" +
               "and (wct.isDeleted is null or wct.isDeleted = false) " +
               "                and wct.medCase_id is null and wct.prepatient_id is null  \n" +
               "                and (wct.prepatientinfo is null or wct.prepatientinfo='') \n" +
               " and wct.reserveType_id "+reserve_type+"\n"+
               "                and case when wcd.calendardate=current_date then case when wct.timefrom > current_time then 1 else 0 end else 1 end =1\n" +
               "                and  vwf.id= "+id_specialization+ //поиск по специализации
               " and coalesce(w.lpu_id,wf.lpu_id) "+lpu_id+"  \n"+
               "                group by wcd.calendardate\n" +
               "                having count (wct.id)>0\n" +
               "                order by wcd.calendardate;";

        return req;
    }

    public  static  String SelectTimeFirst(Integer id_specialization, Integer id_date,Integer codeId)
    {
        LpuIdAndReserveType(codeId);
        String req = "\n" +
                "\n" +
                "select max(wct.id) as id,cast(wct.timeFrom as varchar(5)) as time \n" +
                "     from WorkCalendarTime wct\n" +
                "     left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id\n" +
                "     left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id\n" +
                "     left join workcalendar wc on wc.id=wcd.workcalendar_id\n" +
                "     left join workfunction wf on wf.id=wc.workfunction_id\n" +
                "     left join worker w on w.id=wf.worker_id\n"+
                "     left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
                "     where \n" +
                "    vwf.id= " + id_specialization+
                //" and w.lpu_id "+lpu_id+" \n"+
                " and coalesce(w.lpu_id,wf.lpu_id) "+lpu_id+"  \n"+
                "and (wcd.isDeleted is null or wcd.isDeleted = false)  \n" +
                "and (wct.isDeleted is null or wct.isDeleted = false) " +
                " and (wf.archival is null or wf.archival='0')" +
                " and (wf.isnoviewremoteuser  ='0' or wf.isnoviewremoteuser is null)" +
                " and wcd.calendardate = (select calendardate from workcalendarday where id = "+id_date+")"+
                " and wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') " +
                " and wct.reserveType_id "+reserve_type+""+
                " and (wcd.calendardate>current_date or (wcd.calendardate=current_date and wct.timefrom> current_time)) " +
                " group by wct.timeFrom" +
                " order by wct.timeFrom";
        return req;
    }

    public  static  String SelectPatients(Integer bithdate) {
        String req = "select id, lastname, firstname, middlename,to_char(birthday,'dd.mm.yyyy') as birthday  " +
                "from Patient " +
                "where noactuality is null" +
                "and deathdate is null " +
                "and birthday ='"+bithdate+"'"; //19890801
        return req;
    }

    public  static  String UpdatePatient(Integer patient_id, Integer time_id) {
        String req ="update WorkCalendarTime" +
                " set" +
                " createprerecord = 'MedVox'," +
                " createdateprerecord = current_date," +
                " createtimeprerecord= current_time," +
                " prepatient_id = " +patient_id+
                " where id= " +time_id+
                " and prepatient_id is null and (prepatientinfo is null or prepatientinfo='') and medcase_id is null"+
                " returning id";
        return req;
    }

    public static String SelectUnknownPatient(String lastname,String firstname, String middlename, String birthday) {
       String req = "Select id from Patient" +
               " where (noactuality is null or noactuality='0')" +
               " and deathdate is null" +
               " and birthday ='"+birthday+"'" +
               " and lastname = '"+lastname+"'" +
               " and firstname='"+firstname+"' and middlename='"+middlename+"'";

       return req;
    }
    public static String RecordUnknownPatient(String FIO, String birthday,String phone, Integer time_id) {
       String req = "update WorkCalendarTime " +
               " set" +
               " createprerecord = 'FromSite'," +
               " createdateprerecord = current_date," +
               " createtimeprerecord= current_time," +
               " prepatientinfo ='"+FIO+" "+birthday+"'," +
               " phone='"+phone+"' " +
               " where id= "+time_id+""+
               " and prepatient_id is null and (prepatientinfo is null or prepatientinfo='') and medcase_id is null" +
               " returning id;";

       return req;
    }

    public static String RecordKnownPatient(Integer patietnId, String phone,Integer time_id) {
        String req = "update WorkCalendarTime " +
                " set" +
                " createprerecord = 'FromSite'," +
                " createdateprerecord = current_date," +
                " createtimeprerecord= current_time," +
                " prepatient_id ='"+patietnId+"'," +
                " phone = '"+phone+"'" +
                //"prepatientinfo ='"+FIO+"' " +
                " where id= "+time_id+""+
                " and prepatient_id is null and (prepatientinfo is null or prepatientinfo='') and medcase_id is null" +
                " returning id;";

        return req;
    }

      public static  String CheckPatientEmpty(Integer time_id) {
        String req ="select coalesce(prepatient_id,case when prepatientInfo is null or prePatientInfo='' then null else 1 end) from WorkCalendarTime where id="+time_id;
        return req;
    }
}
