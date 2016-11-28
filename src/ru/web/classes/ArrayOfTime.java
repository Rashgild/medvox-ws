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
@XmlType(name = "ArrayOfTime", propOrder = {
        "times"
})
public class ArrayOfTime {

    @XmlElement(name = "Times", nillable = true)
    protected List<Time> times;

    public List<Time> getTimes() {
        if (times == null) {
            times = new ArrayList<Time>();
        }
        return this.times;
    }

    public void setTimes(List<Time> list)
    {
        this.times = list;
    }
}
