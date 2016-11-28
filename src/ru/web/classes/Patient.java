package ru.web.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by rkurbanov on 02.08.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = {
        "id",
        "lastname",
        "firstname",
        "middlename",
        "birthday"
})
public class Patient {
    @XmlElement(name = "Id")
    protected Integer id;
    @XmlElement(name = "lastname")
    protected String lastname;
    @XmlElement(name = "firstname")
    protected String firstname;
    @XmlElement(name = "middlename")
    protected String middlename;
    @XmlElement(name = "birthday")
    protected String birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer value) {
        this.id = value;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String value) {
        this.birthday = value;
    }
}
