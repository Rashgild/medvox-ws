package ru.web.classes;

import javax.xml.bind.annotation.*;

/**
 * Created by rkurbanov on 01.08.16.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Doctor", propOrder = {
        "id",
        "name",
        "lastname",
       "firstname",
        "middlename"
})
public class Doctor {
    @XmlElement(name = "Id")
    protected Integer id;
    @XmlElement(name = "name")
    protected String name;
   @XmlElement(name = "lastname")
    protected String lastname;
   @XmlElement(name = "firstname")
    protected String firstname;
    @XmlElement(name = "middlename")
    protected String middlename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

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
}