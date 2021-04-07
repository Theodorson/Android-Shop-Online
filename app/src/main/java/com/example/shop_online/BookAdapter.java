package com.example.shop_online;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public ArrayList<Book> localDataSet;
    public ArrayList<Bitmap> bitmapImages;


    public BookAdapter(ArrayList<Book> dataSet){
        this.localDataSet = dataSet;
        this.bitmapImages = new ArrayList<>();
        for (int i=0; i<localDataSet.size(); i++){
            setImages(i,localDataSet.get(i).imageLink);
        }
    }

    public void setImages(int index,String imageLink){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                    bitmapImages.set(index,getBitmapFromURL(imageLink));
                    Log.i("set","setImage");

            }
        };
        Executors.newSingleThreadScheduledExecutor().execute(runnable);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.i("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.i("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Exception", e.getMessage());
            return null;
        }
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup,false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder viewHolder, int position) {
        viewHolder.bind(localDataSet.get(position), bitmapImages.get(position));
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookName, bookAuthor, bookPrice;
        private final ImageView bookImage;


        public BookViewHolder(View v) {
            super(v);
            bookName = v.findViewById(R.id.BookText);
            bookAuthor = v.findViewById(R.id.AuthorText);
            bookPrice = v.findViewById(R.id.PriceText);
            bookImage = v.findViewById(R.id.bookImage);

        }

        public void bind(Book book, Bitmap bitmap) {

            bookName.setText(book.getName());
            String author = "Author: " + book.getAuthor();
            bookAuthor.setText(author);
            String price = Float.toString(book.getPrice()) + " €";
            bookPrice.setText(price);
            //bookImage.setImageBitmap(bitmap);
            //setImage(book.imageLink);
        }

        public void setImage(String imageLink){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    bookImage.setImageBitmap(getBitmapFromURL(imageLink));
                }
            };
            Executors.newSingleThreadScheduledExecutor().execute(runnable);
        }








    }

}
