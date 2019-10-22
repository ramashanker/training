package com.kafka.app.message;

public class PayloadData {
    String key;
    String message;
    public PayloadData(String key,String message){
        this.key=key;
        this.message=message;
    }

    public PayloadData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PayloadData{" +
                "key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
