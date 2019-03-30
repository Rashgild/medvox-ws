package ru.soap.ws.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAllinformation", propOrder = {
        "alinfos"
})
public class AllInformationList {

    @XmlElement(name = "Alinfos", nillable = true)
    private List<AllInformation> alinfos;


    public List<AllInformation> getAllinformation() {
        if (alinfos == null) {
            alinfos = new ArrayList<>();
        }
        return this.alinfos;
    }

    public void setAllinfomation(List<AllInformation> list)
    {
        this.alinfos = list;
    }
}
