package com.example.shop_online;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder{
    // list_item fields
    private final TextView bookName, bookAuthor, bookPrice;
    private final ImageView bookImage;

    public BookViewHolder(View v) {
        super(v);
        bookName = v.findViewById(R.id.BookText);
        bookAuthor = v.findViewById(R.id.AuthorText);
        bookPrice = v.findViewById(R.id.PriceText);
        bookImage = v.findViewById(R.id.cartBookImage);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public void setBookName(String name){
        bookName.setText(name);
    }
    public void setBookAuthor(String author){
        bookAuthor.setText(author);
    }
    public void setBookPrice(float price){
        bookPrice.setText(String.valueOf(price) + " €");
    }

    public ImageView getImageView (){
        return this.bookImage;
    }


    private BookViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(BookViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}