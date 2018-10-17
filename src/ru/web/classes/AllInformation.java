package ru.web.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by rkurbanov on 02.08.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllInformation", propOrder = {
        "name",
        "lastname",
        "firstname",
        "middlename",
        "date",
        "time",
        "idtime"
})
public class AllInformation {
    @XmlElement(name = "name")
    protected String name;
    @XmlElement(name = "lastname")
    protected String lastname;
    @XmlElement(name = "firstname")
    protected String firstname;
    @XmlElement(name = "middlename")
    protected String middlename;
    @XmlElement(name = "date")
    protected String date;
    @XmlElement(name = "time")
    protected String time;
    @XmlElement(name = "idtime")
    protected String idtime;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String value) {
        this.lastname = value;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String value) {
        this.firstname = value;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String value) {
        this.middlename = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String value) {
        this.time = value;
    }

    public String getIdtime() {
        return idtime;
    }

    public void setIdtime(String idtime) {
        this.idtime = idtime;
    }
}
