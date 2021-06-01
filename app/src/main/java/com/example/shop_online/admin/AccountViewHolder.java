package com.example.shop_online.admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_online.R;

public class AccountViewHolder extends RecyclerView.ViewHolder{

    private final TextView accountName, accountEmail, accountType;
    private final ImageView accountImage;

    public AccountViewHolder(View v) {
        super(v);
        accountName = v.findViewById(R.id.accountNameText);
        accountEmail = v.findViewById(R.id.emailAccountText);
        accountType = v.findViewById(R.id.accountTypeText);
        accountImage = v.findViewById(R.id.accountImageView);

    }

    public void setAccountName (String text) {accountName.setText(text);}
    public void setAccountEmail(String text) {accountEmail.setText(text);}
    public void setAccountType (String text) {accountType.setText(text);}

    public ImageView getAccountImage() {
        return accountImage;
    }
}