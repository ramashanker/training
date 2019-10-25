package com.kafka.app.message.consume;

import java.io.Serializable;
import java.time.LocalDate;

public class PayloadData implements Serializable {
    String key;
    LocalDate localDate;
    public PayloadData(String key, LocalDate localDate){
        this.key=key;
        this.localDate=localDate;
    }
}
