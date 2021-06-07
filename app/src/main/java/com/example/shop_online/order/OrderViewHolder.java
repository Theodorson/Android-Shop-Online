package com.example.shop_online.order;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;
import com.example.shop_online.book.BookViewHolder;

public class OrderViewHolder extends RecyclerView.ViewHolder{

   private final TextView textOrderName, textOrderPrice, textOrderDetails;
   private OrderViewHolder.ClickListener mClickListener;

    public OrderViewHolder (View v) {
        super(v);
       textOrderName = v.findViewById(R.id.orderDetailsNameIdText);
       textOrderPrice = v.findViewById(R.id.orderPriceText);
       textOrderDetails = v.findViewById(R.id.orderDetailsText);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
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



    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(OrderViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}