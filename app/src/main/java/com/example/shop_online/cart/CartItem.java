package com.example.shop_online.cart;

public class CartItem {
    private String name, imageLink;
    private int quantity, index;
    private float price;


    public CartItem(){
    };

    public String getImageLink() {
        return imageLink;
    }

    public CartItem(String name, String imageLink, int quantity, float price){
        this.name = name;
        this.imageLink = imageLink;
        this.quantity = quantity;
        this.price = price;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
