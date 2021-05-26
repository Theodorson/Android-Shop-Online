package com.example.shop_online.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop_online.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private TextView textNrBooks, textNrOrders, textNrAccounts, textNrBooksSold, textTotalEarn, textMostSoldBook;
    private Button btnAccounts, btnNewBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        textNrBooks = findViewById(R.id.textViewNumberOfBooks);
        textNrOrders = findViewById(R.id.textViewNumberOfOrders);
        textNrAccounts = findViewById(R.id.textViewNumberOfAccounts);
        textNrBooksSold = findViewById(R.id.textViewNumberOfBooksSold);
        textTotalEarn = findViewById(R.id.textViewTotalEarn);
        textMostSoldBook= findViewById(R.id.textViewMostSoldBook);
        btnAccounts = findViewById(R.id.accountsAdminButton);
        btnNewBook = findViewById(R.id.newBookButton);

        btnAccounts.setOnClickListener(this);
        btnNewBook.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accountsAdminButton:
                Intent intent = new Intent(this, AccountsActivity.class);
                startActivity(intent);
                break;
            case R.id.newBookButton:
                Intent intent1 = new Intent(this, AccountsActivity.class);
                startActivity(intent1);
                break;
        }
    }


    void initAllTexts(){

    }

}