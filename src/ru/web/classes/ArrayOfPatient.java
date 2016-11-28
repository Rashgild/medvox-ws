package ru.web.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkurbanov on 02.08.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPatient", propOrder = {
        "patients"
})
public class ArrayOfPatient {
    @XmlElement(name = "Patients", nillable = true)
    protected List<Patient> patients;


    public List<Patient> getPatients() {
        if (patients == null) {
            patients = new ArrayList<Patient>();
        }
        return this.patients;
    }

    public void setPatients(List<Patient> list)
    {
        this.patients = list;
    }
}
