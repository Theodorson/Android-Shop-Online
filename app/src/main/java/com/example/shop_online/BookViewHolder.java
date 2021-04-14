package com.example.shop_online;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // list_item fields
    private final TextView bookName, bookAuthor, bookPrice;
    private final ImageView bookImage;

    public BookViewHolder(View v) {
        super(v);
        bookName = v.findViewById(R.id.BookText);
        bookAuthor = v.findViewById(R.id.AuthorText);
        bookPrice = v.findViewById(R.id.PriceText);
        bookImage = v.findViewById(R.id.BookImage);
    }

    public void setBookName(String name){
        bookName.setText(name);
    }
    public void setBookAuthor(String author){
        bookAuthor.setText(author);
    }
    public void setBookPrice(Float price){
        bookPrice.setText(price.toString()+" €");
    }

    public ImageView getImageView (){
        return this.bookImage;
    }

    @Override
    public void onClick(View v) {

    }
}