package com.example.shop_online;

public class Order {
    private String county, street, village, userName, date;

    public String getDate() {
        return date;
    }

    private float price;


    public Order(){}


    public Order(String county, String street, String village, String userName, String date,float price) {
        this.county = county;
        this.street = street;
        this.village = village;
        this.userName = userName;
        this.date = date;
        this.price = price;
    }

    public String getCounty() {
        return county;
    }

    public String getStreet() {
        return street;
    }

    public String getVillage() {
        return village;
    }

    public String getUserName() {
        return userName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
