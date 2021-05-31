package com.example.shop_online.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop_online.MainActivity;
import com.example.shop_online.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private TextView textNrBooks, textNrOrders, textNrAccounts, textNrBooksSold, textTotalEarn, textMostSoldBook;
    private Button btnAccounts, btnNewBook, btnBack;

    private String nrOfModelsBooks;
    private String nrOfAccounts;
    private String nrOfOrders;
    private String totalEarn;
    private String nrOfBooksSold;

    private long orders;
    private float incomeMoney;
    private int booksSold;

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
        btnBack = findViewById(R.id.backProfileButton);

        btnAccounts.setOnClickListener(this);
        btnNewBook.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        initAllTexts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accountsAdminButton:
                Intent intent = new Intent(this, AccountsActivity.class);
                startActivity(intent);
                break;
            case R.id.newBookButton:
                Bundle bundle = new Bundle();
                bundle.putInt("activity", 2);
                Intent intent1 = new Intent(this, NewBookActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.backProfileButton:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;

        }
    }


    void initAllTexts(){
        nrOfModelsBooks = "Number of copies: ";
        nrOfAccounts = "Number of accounts: ";
        nrOfOrders = "Number of orders: ";
        totalEarn = "Total earn: ";
        nrOfBooksSold = "Quantity of books sold: ";
        orders = 0;
        incomeMoney = 0.f;
        booksSold = 0;


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nrOfModelsBooks += String.valueOf(snapshot.getChildrenCount());
                textNrBooks.setText(nrOfModelsBooks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            nrOfAccounts += String.valueOf(snapshot.getChildrenCount());
            textNrAccounts.setText(nrOfAccounts);

            for (DataSnapshot user: snapshot.getChildren()){
               orders += user.child("orders").getChildrenCount();
               for (DataSnapshot order: user.child("orders").getChildren()){
                   incomeMoney += Float.parseFloat(order.child("details").child("price").getValue().toString());
                   for (DataSnapshot cart: order.child("cart").getChildren()){
                       booksSold += Integer.parseInt(cart.child("quantity").getValue().toString());
                   }
               }
            }

            nrOfOrders += String.valueOf(orders);
            totalEarn += String.valueOf(incomeMoney);
            nrOfBooksSold += String.valueOf(booksSold);
            textNrOrders.setText(nrOfOrders);
            textTotalEarn.setText(totalEarn);
            textNrBooksSold.setText(nrOfBooksSold);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}