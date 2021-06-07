package com.example.shop_online.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;


public class OrderCartViewHolder extends RecyclerView.ViewHolder{

    private final TextView textName, textQuantity, textPrice;
    private final ImageView imageView;

    public OrderCartViewHolder (View v) {
        super(v);
       textName = v.findViewById(R.id.orderCartNameText);
       textQuantity = v.findViewById(R.id.orderCartQuantityText);
       textPrice = v.findViewById(R.id.orderCartPriceText);
       imageView = v.findViewById(R.id.orderCartImageView);


    }

    public void setTextName (String text){ textName.setText(text);}
    public void setTextQuantity (String text){ textQuantity.setText("Quantity: " + text);}
    public void setTextPrice (String text){textPrice.setText(text + " €");}

    public ImageView getImageView() {
        return imageView;
    }
}