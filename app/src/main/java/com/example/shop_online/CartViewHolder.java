package com.example.shop_online;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder{

    private final TextView cartItemName, cartItemQuantity, cartItemPrice;
    private final ImageView cartItemImage;
    private final Button addBtn, minusBtn, deleteBtn;

    public CartViewHolder (View v) {
        super(v);
       cartItemName = v.findViewById(R.id.cartBookName);
       cartItemQuantity = v.findViewById(R.id.cartBookQuantity);
       cartItemPrice = v.findViewById(R.id.cartBookPrice);
       cartItemImage = v.findViewById(R.id.cartBookImage);
       addBtn = v.findViewById(R.id.addBookButton);
       minusBtn = v.findViewById(R.id.minusBookButton);
       deleteBtn = v.findViewById(R.id.deleteBookButton);

    }

    public void setCartItemName(String name){
        cartItemName.setText(name);
    }

    public void setCartItemQuantity(int quantity){
        cartItemQuantity.setText(String.valueOf(quantity));
    }

    public void setCartItemPrice(float price){
        cartItemPrice.setText(String.valueOf(price) + " €");
    }

    public ImageView getCartImage(){
        return cartItemImage;
    }





}