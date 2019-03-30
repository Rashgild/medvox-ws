package ru.soap.ws.utils;

public class SqlRequest {

    private static String reserve_type = "";
    private static String lpu_id = "";

    private static void lpuIdAndReserveType(Integer id) {

        if (id == 180) {
            lpu_id = " = 180 ";
            reserve_type = "is null ";
        }
        if (id == 246) {
            lpu_id = "in(180,246)";
            reserve_type = " = 3";
        }
    }

    public static String selectSpecialization(Integer id) {
        lpuIdAndReserveType(id);

        String req = "select vwf.id ,vwf.name" +
                " from WorkFunction as wf " +
                " left join Worker as w on w.id = wf.worker_id" +
                " left join Patient as p on p.id = w.person_id" +
                " inner join VocWorkFunction vwf on vwf.id = wf.workFunction_id" +
                " left join WorkCalendar wc on wc.workFunction_id = wf.id" +
                " left join workcalendarday wcd on wcd.workcalendar_id = wc.id" +
                " left join workcalendartime wct on wct.workCalendarDay_id = wcd.id" +
                " left join mislpu mlGr on mlGr.id = wf.lpu_id" +
                " where wc.id is not null " +
                " and (wcd.isDeleted is null or wcd.isDeleted = false) " +
                " and (wct.isDeleted is null or wct.isDeleted = false) " +
                " and wcd.calendardate > = current_date" +
                " and wf.group_id is null " +
                " and (wf.archival is null or wf.archival = '0')" +
                " and (wf.isnoviewremoteuser   = '0' or wf.isnoviewremoteuser is null)" +
                " and coalesce(w.lpu_id,wf.lpu_id) " + lpu_id + " " +
                " and wct.medCase_id is null and wct.prepatient_id is null " +
                " and (wct.prepatientinfo is null or wct.prepatientinfo = '') " +
                " and wct.reserveType_id " + reserve_type + " " +
                " group by vwf.id, vwf.name" +
                " having count(wct.id)>0;";

        return req;

    }

    public static String selectDoctor(Integer id_spec, Integer codeId) {
        lpuIdAndReserveType(codeId);

        String req = "SELECT" +
                " wf.id , vwf.name, case when wf.dtype = 'PersonalWorkFunction' then p.lastname" +
                " else wf.groupname end as lastname," +
                " coalesce(p.firstname,'') as firstname, coalesce(p.middlename,'') as middlename" +
                " from WorkFunction as wf" +
                " left join Worker as w on w.id = wf.worker_id" +
                " left join Patient as p on p.id = w.person_id" +
                " inner join VocWorkFunction vwf on vwf.id = wf.workFunction_id" +
                " left join WorkCalendar wc on wc.workFunction_id = wf.id" +
                " left join mislpu mlGr on mlGr.id = wf.lpu_id" +
                " left join workcalendarday wcd on wcd.workcalendar_id = wc.id" +
                " left join workcalendartime wct on wct.workCalendarDay_id = wcd.id" +
                " where wc.id is not null" +
                " and (wcd.isDeleted is null or wcd.isDeleted = '0') and (wct.isDeleted is null or wct.isDeleted = '0')" +
                " and wf.group_id is null" +
                " and (wf.archival is null or wf.archival = '0')" +
                " and (wf.isnoviewremoteuser   = '0' or wf.isnoviewremoteuser is null)" +
                " and coalesce(wf.lpu_id,w.lpu_id) " + lpu_id + " " +
                " and wct.prepatient_id is null" +
                " and (wct.prepatientinfo is null or wct.prepatientinfo = '') " +
                " and wct.reserveType_id " + reserve_type + " " +
                " and wct.medCase_id is null" +
                " and wct.createprerecord is null" +
                " and wcd.calendardate > = current_date" +
                " and vwf.id = " + id_spec + " " +
                " group by wf.id, vwf.name, p.lastname ,p.firstname,p.middlename" +
                " having count(wct.id)>0";

        System.out.println(req);
        return req;
    }

    public static String selectDateSecond(Integer id_doctor, Integer id_specialization, Integer codeId) {
        lpuIdAndReserveType(codeId);

        String req = "select max(wcd.id) as id, to_char(wcd.calendardate,'dd.mm.yyyy') as date" +
                " from workCalendar wc " +
                " left join workcalendarday wcd on wcd.workcalendar_id = wc.id" +
                " left join workcalendartime wct on wct.workcalendarday_id = wcd.id" +
                " left join workfunction wf on wf.id = wc.workfunction_id" +
                " left join vocworkfunction vwf on vwf.id = wf.workfunction_id" +
                " where " +
                " wcd.calendardate > = current_date" +
                " and (wcd.isDeleted is null or wcd.isDeleted = '0') and (wct.isDeleted is null or wct.isDeleted = '0') " +
                " and wct.medCase_id is null and wct.prepatient_id is null " +
                " and (wct.prepatientinfo is null or wct.prepatientinfo = '') " +
                " and wct.reserveType_id " + reserve_type + "" +
                " and wf.id = " + id_doctor + "" + //поиск по сотруднику
                " and vwf.id = " + id_specialization + " " + //поиск по специализации
                " and case when wcd.calendardate = current_date then case when wct.timefrom > current_time then 1 else 0 end else 1 end = 1" +
                " group by wcd.calendardate" +
                " having count (wct.id)>0" +
                " order by wcd.calendardate";
        return req;
    }

    public static String selectTimeSecond(Integer id_date, Integer codeId) {
        lpuIdAndReserveType(codeId);
        String req = "select max(wct.id) as id,cast(wct.timeFrom as varchar(5)) as time" +
                " from WorkCalendarTime wct" +
                " left join VocServiceReserveType vsrt on vsrt.id = wct.reserveType_id" +
                " left join WorkCalendarDay wcd on wcd.id = wct.workCalendarDay_id" +
                " left join workcalendar wc on wc.id = wcd.workcalendar_id" +
                " left join workfunction wf on wf.id = wc.workfunction_id" +
                " left join vocworkfunction vwf on vwf.id = wf.workfunction_id" +
                " where " +
                " wct.workCalendarDay_id =   " + id_date + "" +
                " and (wcd.isDeleted is null or wcd.isDeleted = '0') and (wct.isDeleted is null or wct.isDeleted = '0')" +
                " and wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo = '')" +
                " and wct.reserveType_id " + reserve_type + "" +
                " and (wcd.calendardate>current_date or (wcd.calendardate = current_date and wct.timefrom> current_time)) " +
                " group by wct.timeFrom" +
                " order by wct.timeFrom;";
        return req;
    }

    public static String selectAllInformation(Integer id_time) {
        String req = "select vwf.name, " +
                " case when wf.dtype = 'GroupWorkFunction' then wf.groupname else p.lastname end" +
                " ,coalesce (p.firstname,'') as 'firstname'" +
                " ,coalesce (p.middlename,'')as 'middlename',to_char(wcd.calendardate,'dd.mm.yyyy') as date ,cast(wct.timeFrom as varchar(5)) as time" +
                " from WorkFunction as wf " +
                " left join Worker as w on w.id = wf.worker_id " +
                " left join Patient as p on p.id = w.person_id " +
                " inner join VocWorkFunction vwf on vwf.id = wf.workFunction_id " +
                " left join WorkCalendar wc on wc.workFunction_id = wf.id " +
                " left join mislpu mlGr on mlGr.id = wf.lpu_id " +
                " left join workcalendarday wcd on wcd.workcalendar_id = wc.id " +
                " left join workcalendartime wct on wct.workcalendarday_id = wcd.id " +
                " where wc.id is not null " +
                " and wf.group_id is null " +
                " and (wf.archival is null or wf.archival = '0') " +
                " and (wf.isnoviewremoteuser   = '0' or wf.isnoviewremoteuser is null) " +
                " and wct.id = " + id_time;

        return req;
    }

    public static String selectDateFirst(Integer id_specialization, Integer codeId) {
        lpuIdAndReserveType(codeId);
        String req = "select max(wcd.id) as id, to_char(wcd.calendardate,'dd.mm.yyyy') as date" +
                " from workCalendar wc " +
                " left join workcalendarday wcd on wcd.workcalendar_id = wc.id" +
                " left join workcalendartime wct on wct.workcalendarday_id = wcd.id" +
                " left join workfunction wf on wf.id = wc.workfunction_id" +
                " left join worker w on w.id = wf.worker_id" +
                " left join vocworkfunction vwf on vwf.id = wf.workfunction_id" +
                " where " +
                " wcd.calendardate > =   current_date" +
                " and (wcd.isDeleted is null or wcd.isDeleted = false) " +
                " and (wct.isDeleted is null or wct.isDeleted = false) " +
                " and wct.medCase_id is null and wct.prepatient_id is null " +
                " and (wct.prepatientinfo is null or wct.prepatientinfo = '') " +
                " and wct.reserveType_id " + reserve_type + "" +
                " and case when wcd.calendardate = current_date " +
                " then case when wct.timefrom > current_time then 1 else 0 end else 1 end = 1" +
                " and vwf.id = " + id_specialization +
                " and coalesce(w.lpu_id,wf.lpu_id) " + lpu_id + " " +
                " group by wcd.calendardate" +
                " having count (wct.id) > 0" +
                " order by wcd.calendardate;";

        return req;
    }

    public static String selectTimeFirst(Integer id_specialization, Integer id_date, Integer codeId) {
        lpuIdAndReserveType(codeId);
        return "select max(wct.id) as id,cast(wct.timeFrom as varchar(5)) as time " +
                " from WorkCalendarTime wct" +
                " left join VocServiceReserveType vsrt on vsrt.id = wct.reserveType_id" +
                " left join WorkCalendarDay wcd on wcd.id = wct.workCalendarDay_id" +
                " left join workcalendar wc on wc.id = wcd.workcalendar_id" +
                " left join workfunction wf on wf.id = wc.workfunction_id" +
                " left join worker w on w.id = wf.worker_id" +
                " left join vocworkfunction vwf on vwf.id = wf.workfunction_id" +
                " where " +
                " vwf.id = " + id_specialization +
                " and coalesce(w.lpu_id,wf.lpu_id) " + lpu_id + " " +
                " and (wcd.isDeleted is null or wcd.isDeleted = false) " +
                " and (wct.isDeleted is null or wct.isDeleted = false) " +
                " and (wf.archival is null or wf.archival = '0')" +
                " and (wf.isnoviewremoteuser = '0' or wf.isnoviewremoteuser is null)" +
                " and wcd.calendardate = (select calendardate from workcalendarday where id = " + id_date + ")" +
                " and wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo = '') " +
                " and wct.reserveType_id " + reserve_type + "" +
                " and (wcd.calendardate>current_date or (wcd.calendardate = current_date and wct.timefrom> current_time)) " +
                " group by wct.timeFrom" +
                " order by wct.timeFrom";
    }

    public static String selectPatients(Integer bithdate) {
        return "select id, lastname, firstname, middlename,to_char(birthday,'dd.mm.yyyy') as birthday " +
                " from Patient " +
                " where noactuality is null" +
                " and deathdate is null " +
                " and birthday = '" + bithdate + "'";
    }

    public static String updatePatient(Integer patient_id, Integer time_id) {
        return "update WorkCalendarTime" +
                " set" +
                " createprerecord = 'MedVox'," +
                " createdateprerecord = current_date," +
                " createtimeprerecord = current_time," +
                " prepatient_id = " + patient_id +
                " where id = " + time_id +
                " and prepatient_id is null and (prepatientinfo is null or prepatientinfo = '') and medcase_id is null" +
                " returning id";
    }

    public static String selectUnknownPatient(String lastname, String firstname, String middlename, String birthday) {
        return "Select id from Patient" +
                " where (noactuality is null or noactuality = '0')" +
                " and deathdate is null" +
                " and birthday = '" + birthday + "'" +
                " and lastname = '" + lastname + "'" +
                " and firstname = '" + firstname + "' and middlename = '" + middlename + "'";
    }

    public static String recordUnknownPatient(String FIO, String birthday, String phone, Integer time_id) {
        return "update WorkCalendarTime " +
                " set" +
                " createprerecord = 'FromSite'," +
                " createdateprerecord = current_date," +
                " createtimeprerecord = current_time," +
                " prepatientinfo = '" + FIO + " " + birthday + "'," +
                " phone = '" + phone + "' " +
                " where id = " + time_id + "" +
                " and prepatient_id is null and (prepatientinfo is null or prepatientinfo = '') and medcase_id is null" +
                " returning id;";
    }

    public static String recordKnownPatient(Integer patietnId, String phone, Integer time_id) {
        return "update WorkCalendarTime " +
                " set" +
                " createprerecord = 'FromSite'," +
                " createdateprerecord = current_date," +
                " createtimeprerecord = current_time," +
                " prepatient_id = '" + patietnId + "'," +
                " phone = '" + phone + "'," +
                " wayofrecord_id = 5" +
                " where id = " + time_id + "" +
                " and prepatient_id is null and (prepatientinfo is null or prepatientinfo = '') and medcase_id is null" +
                " returning id;";
    }

    public static String checkPatientEmpty(Integer time_id) {
        return "select coalesce(prepatient_id,case when prepatientInfo is null or prePatientInfo = '' " +
                " then null else 1 end) from WorkCalendarTime where id = " + time_id;
    }
}
