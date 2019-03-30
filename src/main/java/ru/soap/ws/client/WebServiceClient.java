package ru.soap.ws.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import ru.soap.ws.entity.AllInformation;
import ru.soap.ws.entity.AllInformationList;
import ru.soap.ws.entity.Date;
import ru.soap.ws.entity.Doctor;
import ru.soap.ws.entity.Patient;
import ru.soap.ws.entity.Specialization;
import ru.soap.ws.entity.Time;
import ru.soap.ws.service.IWebService;

public class WebServiceClient {

    public static void main(String[] args) throws IOException {

        //URL url = new URL("http://192.168.7.19:1986/webservice/start?wsdl");
        URL url = new URL("http://192.168.2.45:1986/webservice/start?wsdl");
        QName qname = new QName("http://service.web.ru/", "WebServiceImplService");
        Service service = Service.create(url, qname);
        final IWebService start = service.getPort(IWebService.class);

        int i = 0;
        int id_spec = 0,
                id_doc = 0,
                id_date = 0,
                id_time = 0,
                bithdate_patient = 0,
                id_patient = 0;

        String str_id_special = "",
                str_id_doc = "",
                str_id_date = "",
                str_id_time = "",
                str_bithdate_patient = "",
                str_lastname = "р", str_firstname = "р", str_middlename = "р", str_nam = "";


//-----Идентефикация пациента

        System.out.println("Введите вашу дату рождения в формате ГГГГММДД");
        bithdate_patient = CheckInput(str_bithdate_patient, bithdate_patient);

        List<Patient> patients = start.getArrayOfPatient(bithdate_patient).getPatients();
       /* for (Patient s: sp0)
        { i++;
            System.out.println(i+"id="+s.getId()+"ФИО=" +s.getLastname()+" "+
                    s.getFirstname()+" "+s.getMiddlename()+" Дата рождения= "+s.getBirthday());
        }*/
        i = 0;

        String strf = "5";
        System.out.println("Введите Ваши ФИО (одной строкой)");
        strf = InputFio(strf);
        String[] ss = strf.split(" ");
        str_lastname = ss[0];
        str_firstname = ss[1];
        str_middlename = ss[2];
        System.out.println("Здравствуйте, " + str_lastname + " " + str_firstname + " " + str_middlename);

        for (Patient s : patients) {
            if (s.getLastname().equalsIgnoreCase(str_lastname)) {
                id_patient = s.getId();
                System.out.println("Здравствуйте, " + str_lastname + " " + str_firstname + " " + str_middlename);
                break;
            }
        }

        if (id_patient == 0) {
            System.out.println("Мы Вас не нашли у себя в базе...");
            System.exit(0);
        }

//-----Специализации
        List<Specialization> specializations = start.getArrayOfSpecialization().getSpecializations();

        for (Specialization s : specializations) {
            i++;
            System.out.println(i + " Name=" + s.getName() + " id=" + s.getId());
        }
        i = 0;

        System.out.println("Введите Id специальности");
        id_spec = CheckInput(str_id_special, id_spec);
        //System.out.println ("sp="+start.getArrayOfDoctor(id_spec).getDoctors().size());
        List<Doctor> sp1 = start.getArrayOfDoctor(id_spec).getDoctors();
        for (Doctor s : sp1) {
            i++;
            System.out.println(i + " Name=" + s.getName() + " id= " + s.getId() + " ФИО=" + s.getLastname() + " " +
                    s.getFirstname() + " " + s.getMiddlename());
        }
        i = 0;


        System.out.println(" Введите Id врача \n Если хотите перейти к выбору даты - введите 0");
        id_doc = CheckInput(str_id_doc, id_doc);


        //разветвление на выбор врача или...
        if (id_doc > 0) {
            List<Date> sp2 = start.getArrayOfDatesSecond(id_spec, id_doc).getDates();
            for (Date s : sp2) {
                i++;
                System.out.println(i + " Дата= " + s.getDate() + " Id = " + s.getId());
            }
            System.out.println("Введите Id даты");
            id_date = CheckInput(str_id_date, id_date);

            List<Time> sp3 = start.getArrayOfTimesSecond(id_date).getTimes();
            for (Time s : sp3) {
                i++;
                System.out.println(i + " Время= " + s.getTime() + " Id = " + s.getId());
            }
            System.out.println("Введите Id времени ");
            id_time = CheckInput(str_id_time, id_time);
        }

        //...на выбор сразу времени
        if (id_doc == 0) {
            List<Date> sp2 = start.getArrayOfDatesFirst(id_spec).getDates();
            for (Date s : sp2) {
                i++;
                System.out.println(i + " Дата= " + s.getDate() + " Id = " + s.getId());
            }
            System.out.println("Введите Id даты");
            id_date = CheckInput(str_id_date, id_date);

            List<Time> sp3 = start.getArrayOfTimesFirst(id_spec, id_date).getTimes();
            for (Time s : sp3) {
                i++;
                System.out.println(i + " Время= " + s.getTime() + " Id = " + s.getId());
            }
            System.out.println("Введите Id времени ");
            id_time = CheckInput(str_id_time, id_time);
        }
        i = 0;

        //System.out.println("Вы, "+str_lastname+" "+str_firstname+" "+str_middlename+", хотите записаться на прием к "+start.getAllInformation(id_doc, id_spec, id_date, id_time));
       /* List<AllInformation> all  = start.getArrayOfAllinformation(id_time).getAllinformation();
        for (AllInformation s: all)
        { //i++;
            //System.out.println(i+" Name="+s.getName()+" id="+s.getId());

            System.out.println("Вы "+str_lastname+" "+str_firstname+" "+str_middlename+"" +
                    " хотите записаться на прием к "+s.getName()+" "+s.getLastname()+" "+
            s.getFirstname()+" "+s.getMiddlename()+" на "+s.getDate()+" "+s.getTime());
        }*/
        //start.getArr


        //int result2 = start.CheckTimeCalendar(id_time);

        //if(result2==0)
        //{
        //System.out.println("Можно заисать - пусто");


        AllInformationList arr = start.setPatientRecord(id_patient, id_time);
        List<AllInformation> all = arr.getAllinformation();

        for (AllInformation s : all) { //i++;
            //System.out.println(i+" Name="+s.getName()+" id="+s.getId());

            System.out.println("Вы " + str_lastname + " " + str_firstname + " " + str_middlename + "" +
                    " записаны на прием к " + s.getName() + " " + s.getLastname() + " " +
                    s.getFirstname() + " " + s.getMiddlename() + " на " + s.getDate() + " " + s.getTime());
        }
        //start.setUnknownPatientRecord();
        // List<AllInformationList> all = start.getArrayOfAllinformation(id_time);  //start.setPatientRecord(id_patient,id_time).getAllinformation();

        /*    for (AllInformation s: all)
            { //i++;
                //System.out.println(i+" Name="+s.getName()+" id="+s.getId());

                System.out.println("Вы "+str_lastname+" "+str_firstname+" "+str_middlename+"" +
                        "записаны на прием к "+s.getName()+" "+s.getLastname()+" "+
                        s.getFirstname()+" "+s.getMiddlename()+" на "+s.getDate()+" "+s.getTime());
            }
            */


           /* int resultat = start.setPatientRecord(id_patient,id_time);
            if (resultat > 0  ) { System.out.println("Вы успешно записаны, Ваш номер в очереди"+resultat+" :D");}
            else System.out.println("Что-то пошло не так!");*/

        //}
        //else System.out.println("ОЙ! Что-то пошло не так! Давайте попробуем снова?");
    }


    private static int CheckInput(String str, int id) {
        Scanner scan = new Scanner(System.in);
        while (str.matches("^\\d+$") == false) {
            str = scan.nextLine();
            if ((str.matches("^\\d+$"))) {
                id = Integer.parseInt(str);
                return id;
            } else System.out.println("Неверный ввод данных! Повторите попытку: ");
        }
        return id;
    }

    private static String InputFio(String str) {

        Scanner scan = new Scanner(System.in);
        str = scan.nextLine();
        return str;
    }
}

