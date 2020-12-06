package com.rama.spring.app.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class XmlGregorianUtil {

    public static XMLGregorianCalendar dateToXmlGregorian(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance()
                              .newXMLGregorianCalendar(gregorianCalendar);
    }

    public static Date xmlGregorianCalenderToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar getXmlGregorianCalendarNow() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        return DatatypeFactory.newInstance()
                              .newXMLGregorianCalendar(gregorianCalendar);
    }
}
