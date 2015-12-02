package com.lei.lesson08_model;

public class MyMessage {
    private String date;
    private String body;
    private String type;
    private String address;

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MyMessage() {
    }

    public MyMessage(String date, String body, String type, String address) {
        this.date = date;
        this.body = body;
        this.type = type;
        this.address = address;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
