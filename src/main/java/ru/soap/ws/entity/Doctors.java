package ru.soap.ws.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDoctor", propOrder = {
        "doctors"
})
public class Doctors {

    @XmlElement(name = "Doctors", nillable = true)
    private List<Doctor> doctors;


    public List<Doctor> getDoctors() {
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        return this.doctors;
    }

    public void setDoctors(List<Doctor> list) {
        this.doctors = list;
    }
}
