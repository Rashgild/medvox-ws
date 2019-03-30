package ru.soap.ws.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTime", propOrder = {
        "times"
})
public class Times {

    @XmlElement(name = "Times", nillable = true)
    private List<Time> times;

    public List<Time> getTimes() {
        if (times == null) {
            times = new ArrayList<>();
        }
        return this.times;
    }

    public void setTimes(List<Time> list) {
        this.times = list;
    }
}
