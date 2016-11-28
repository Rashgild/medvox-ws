package ru.web.classes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by rkurbanov on 01.08.16.
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Specialization", propOrder = {
        "id",
        "name"
})
public class Specialization {
    @XmlElement(name = "Id")
    protected Integer id;
    @XmlElement(name = "name")
    protected String name;


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

}

