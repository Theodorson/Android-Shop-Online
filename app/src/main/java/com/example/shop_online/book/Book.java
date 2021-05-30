package com.example.shop_online.book;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Book {

    private String name, author, publisher, language, publicationDate, description, imageLink;
    private int id, pages, nrOfModel;
    private float price;


    public Book(){}
    public Book(String name, String author, String publisher, String language, String publicationDate, String description, String imageLink, int pages, int nrOfModel,float price)
    {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.publicationDate = publicationDate;
        this.description = description;
        this.pages = pages;
        this.nrOfModel = nrOfModel;
        this.price = price;
        this.imageLink = imageLink;
    }

    public int getNrOfModel() {
        return nrOfModel;
    }

    public void setNrOfModel(int nrOfModel) {
        this.nrOfModel = nrOfModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
