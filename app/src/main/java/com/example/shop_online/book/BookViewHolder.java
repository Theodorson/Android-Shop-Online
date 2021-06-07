package com.example.shop_online.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    // list_item fields
    private final TextView bookName, bookAuthor, bookPrice;
    private final ImageView bookImage;
    private BookViewHolder.ClickListener mClickListener;

    public BookViewHolder(View v) {
        super(v);
        bookName = v.findViewById(R.id.BookText);
        bookAuthor = v.findViewById(R.id.AuthorText);
        bookPrice = v.findViewById(R.id.PriceText);
        bookImage = v.findViewById(R.id.bookListItemImage);

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


    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(BookViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}