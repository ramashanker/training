package com.rama.spring.app.model;

import com.rama.spring.app.util.XmlGregorianUtil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;

public class CacheMessage implements Serializable {

    private Date expireDate;
    private byte[] message;

    public CacheMessage(Date expireDate, byte[] message){
        this.expireDate = expireDate;
        this.message = message;
    }

    public byte[] getMessage() {
        return message;
    }

    public XMLGregorianCalendar getExpireDate() throws DatatypeConfigurationException {
        return XmlGregorianUtil.dateToXmlGregorian(expireDate);
    }

}
