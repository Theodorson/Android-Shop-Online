package com.example.shop_online.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;

public class BookGridViewHolder extends RecyclerView.ViewHolder{
    // list_item fields
    private final TextView bookGridName, bookGridAuthor, bookGridPrice;
    private final ImageView bookGridImage;

    public BookGridViewHolder(View v) {
        super(v);
        bookGridName = v.findViewById(R.id.BookGridText);
        bookGridAuthor = v.findViewById(R.id.AuthorGridText);
        bookGridPrice = v.findViewById(R.id.PriceGridText);
        bookGridImage = v.findViewById(R.id.bookListItemGridImage);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public void setBookGridName(String name){
        bookGridName.setText(name);
    }
    public void setBookGridAuthor(String author){
        bookGridAuthor.setText(author);
    }
    public void setBookGridPrice(float price){
        bookGridPrice.setText(String.valueOf(price) + " €");
    }

    public ImageView getImageView (){
        return this.bookGridImage;
    }


    private BookGridViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(BookGridViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}