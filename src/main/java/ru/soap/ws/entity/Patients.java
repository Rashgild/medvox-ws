package ru.soap.ws.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPatient", propOrder = {
        "patients"
})
public class Patients {

    @XmlElement(name = "Patients", nillable = true)
    private List<Patient> patients;


    public List<Patient> getPatients() {
        if (patients == null) {
            patients = new ArrayList<>();
        }
        return this.patients;
    }

    public void setPatients(List<Patient> list)
    {
        this.patients = list;
    }
}
