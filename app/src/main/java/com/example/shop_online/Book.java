package com.example.shop_online;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Book {

    private String id, name, author, publisher, language, publicationDate, description, imageLink;
    private int pages;
    private float price;
    private Bitmap image;

    public Book(){}
    public Book(String name, String author, String publisher, String language, String publicationDate, String description, String imageLink, int pages, float price)
    {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.publicationDate = publicationDate;
        this.description = description;
        this.pages = pages;
        this.price = price;
        this.imageLink = imageLink;
    }


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLanguage() {
        return language;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public float getPrice() {
        return price;
    }

    public int getPages() {
        return pages;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
