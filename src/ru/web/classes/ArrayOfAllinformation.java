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
@XmlType(name = "ArrayOfAllinformation", propOrder = {
        "alinfos"
})
public class ArrayOfAllinformation {
    @XmlElement(name = "Alinfos", nillable = true)
    protected List<AllInformation> alinfos;


    public List<AllInformation> getAllinformation() {
        if (alinfos == null) {
            alinfos = new ArrayList<AllInformation>();
        }
        return this.alinfos;
    }

    public void setAllinfomation(List<AllInformation> list)
    {
        this.alinfos = list;
    }
}
