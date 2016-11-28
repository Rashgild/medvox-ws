package ru.web.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkurbanov on 03.08.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDate", propOrder = {
        "dates"
})
public class ArrayOfDate {


    @XmlElement(name = "Dates", nillable = true)
    protected List<Date> dates;


    public List<Date> getDates() {
        if (dates == null) {
            dates = new ArrayList<Date>();
        }
        return this.dates;
    }

    public void setDates(List<Date> list)
    {
        this.dates = list;
    }
}
