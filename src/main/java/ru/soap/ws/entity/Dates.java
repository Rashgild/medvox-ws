package ru.soap.ws.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDate", propOrder = {
        "dates"
})
public class Dates {

    @XmlElement(name = "Dates", nillable = true)
    private List<Date> dates;


    public List<Date> getDates() {
        if (dates == null) {
            dates = new ArrayList<>();
        }
        return this.dates;
    }

    public void setDates(List<Date> list)
    {
        this.dates = list;
    }
}
