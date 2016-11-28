package ru.web.classes;

/**
 * Created by rkurbanov on 02.08.16.
 */

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.web.classes.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDoctor", propOrder = {
        "doctors"
})
public class ArrayOfDoctor {
    @XmlElement(name = "Doctors", nillable = true)
    protected List<Doctor> doctors;


    public List<Doctor> getDoctors() {
        if (doctors == null) {
            doctors = new ArrayList<Doctor>();
        }
        return this.doctors;
    }

    public void setDoctors(List<Doctor> list)
    {
        this.doctors = list;
    }
}
