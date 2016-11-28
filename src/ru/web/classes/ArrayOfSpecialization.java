package ru.web.classes;//

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.web.classes.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSpecialization", propOrder = {
        "specializations"
})
public class ArrayOfSpecialization {

    @XmlElement(name = "Specializations", nillable = true)
    protected List<Specialization> specializations;


    public List<Specialization> getSpecializations() {
        if (specializations == null) {
            specializations = new ArrayList<Specialization>();
        }
        return this.specializations;
    }

    public void setSpecializations(List<Specialization> list)
    {
        this.specializations = list;
    }
}
