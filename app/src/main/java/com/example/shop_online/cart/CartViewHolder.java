package com.example.shop_online.cart;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;

public class CartViewHolder extends RecyclerView.ViewHolder{

    private final TextView cartItemName, cartItemQuantity, cartItemPrice;
    private final ImageView cartItemImage;
    private final Button addBtn, minusBtn, deleteBtn;
    private int quantityItem;
    private float priceItem;

    public CartViewHolder (View v) {
        super(v);
       cartItemName = v.findViewById(R.id.cartBookName);
       cartItemQuantity = v.findViewById(R.id.cartBookQuantity);
       cartItemPrice = v.findViewById(R.id.cartBookPrice);
       cartItemImage = v.findViewById(R.id.bookListItemImage);
       addBtn = v.findViewById(R.id.addBookButton);
       minusBtn = v.findViewById(R.id.minusBookButton);
       deleteBtn = v.findViewById(R.id.deleteBookButton);

    }

    public void incrementQuantity(){
        quantityItem++;
    }
    public void decrementQuantity(){
        if ( quantityItem > 1)
            quantityItem--;
    }

    public void setCartItemName(String name){
        cartItemName.setText(name);
    }

    public void setCartItemQuantity(int quantity){
        cartItemQuantity.setText(String.valueOf(quantity));
        quantityItem = quantity;
    }

    public void setCartItemPrice(float price){
        priceItem = price;
        float totalPrice = priceItem * quantityItem;
        cartItemPrice.setText(String.valueOf(totalPrice) + " €");
    }

    public ImageView getCartImage(){
        return cartItemImage;
    }
    public Button getAddBtn(){return  addBtn;}
    public Button getMinusBtn(){return minusBtn;}
    public Button getDeleteBtn(){return deleteBtn;}
    public int getQuantityItem(){return quantityItem;}

}