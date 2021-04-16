package com.example.shop_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class BookItemActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView bookImage;
    private TextView bookNameText, bookAuthorText, bookLanguageText, bookDescriptionText, bookPublisherText, bookPublicationDateText, bookPriceText, bookPagesText, bookDetailsText;
    private Button buttonBack;
    private FloatingActionButton addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);

        bookImage = findViewById(R.id.bookItemImage);
        bookNameText = findViewById(R.id.bookItemName);
        bookAuthorText = findViewById(R.id.bookAuthorItem);
        bookLanguageText = findViewById(R.id.bookLanguageItem);
        bookPublisherText = findViewById(R.id.bookPublisherItem);
        bookPublicationDateText = findViewById(R.id.bookPublicationDateItem);
        bookPagesText = findViewById(R.id.bookPagesItem);
        bookPriceText = findViewById(R.id.bookPriceItem);
        bookDescriptionText = findViewById(R.id.bookDescriptionItem);
        bookDetailsText = findViewById(R.id.bookDetailsItem);
        bookDetailsText.setText("Details");

        buttonBack = findViewById(R.id.backHomeButton);
        buttonBack.setOnClickListener(this);

        addToCartButton = findViewById(R.id.addToCartFloatingButton);
        addToCartButton.setOnClickListener(this);



        initBookItem();
    }

    public void initBookItem(){
        Intent intent = getIntent();
        bookNameText.setText(intent.getExtras().getString("book name"));
        bookPriceText.setText(intent.getExtras().getString("book price") + "€");
        bookAuthorText.setText("Author: " + intent.getExtras().getString("book author"));
        bookLanguageText.setText("Language: " + intent.getExtras().getString("book language"));
        bookPublisherText.setText("Publisher: " + intent.getExtras().getString("book publisher"));
        bookPublicationDateText.setText("Publication date: " + intent.getExtras().getString("book publication date"));
        bookPagesText.setText("Pages: " + intent.getExtras().getString("book pages"));
        bookDescriptionText.setText("Description: \n" + intent.getExtras().getString("book description"));
        Picasso.get().load(intent.getExtras().getString("book image link")).resize(150,180).into(bookImage);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backHomeButton:
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.addToCartFloatingButton:
                Log.i("Button", "Add To cart");
                break;
        }
    }
}