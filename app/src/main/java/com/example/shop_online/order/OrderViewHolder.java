package com.example.shop_online.order;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;

public class OrderViewHolder extends RecyclerView.ViewHolder{

   private final TextView textOrderName, textOrderPrice, textOrderDetails;

    public OrderViewHolder (View v) {
        super(v);
       textOrderName = v.findViewById(R.id.orderNameIdText);
       textOrderPrice = v.findViewById(R.id.orderPriceText);
       textOrderDetails = v.findViewById(R.id.orderDetailsText);


    }

    public void setTextOrderName(String textOrderName) {
        this.textOrderName.setText(textOrderName);
    }

    public void setTextOrderPrice(float textOrderPrice) {
        this.textOrderPrice.setText(String.valueOf(textOrderPrice) + " €");
    }

    public void setTextOrderDetails(String textOrderDetails) {
        this.textOrderDetails.setText(textOrderDetails);
    }

}