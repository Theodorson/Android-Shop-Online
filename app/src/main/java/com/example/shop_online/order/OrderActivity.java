package com.example.shop_online.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shop_online.MainActivity;
import com.example.shop_online.R;
import com.example.shop_online.cart.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private TextView orderUserNameText, orderCountyText, orderStreetText, orderVillageText, orderPriceText;
    private Button orderProceedBtn;
    private ProgressBar orderProgressBar;

    private ArrayList<CartItem> cartItems;
    private AlertDialog.Builder dialog;

    private float totalPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        cartItems = new ArrayList<>();
        getCartItems();
        orderProgressBar = findViewById(R.id.progressBarOrder);
        orderUserNameText = findViewById(R.id.TextUserNameOrder);
        getUserNameFromDatabaseAndSetText();
        orderPriceText = findViewById(R.id.TextPriceOrder);
        orderCountyText = findViewById(R.id.TextCountyOrder);
        orderStreetText = findViewById(R.id.TextStreetOrder);
        orderVillageText = findViewById(R.id.TextVillageOrder);
        orderProceedBtn = findViewById(R.id.ProceedOrderButton);
        orderProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOrder();
            }
        });


        dialog = new AlertDialog.Builder(OrderActivity.this);


    }



    public void getUserNameFromDatabaseAndSetText(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("lastName").getValue().toString() + " " + snapshot.child("firstName").getValue().toString();
                orderUserNameText.setText(userName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void setDialog(boolean success){
        if (success){
            dialog.setTitle("Success!");
            dialog.setMessage("Your order has been processed successfully");
            dialog.setPositiveButton("Go back to shop!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToFragment(R.id.homeFragment);
                    finish();
                    finishAffinity();
                    dialog.cancel();
                }
            });
        }
        else{
            dialog.setTitle("Failed!");
            dialog.setMessage("Your order has not been processed");
            dialog.setPositiveButton("Try again!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                }
            });
        }

    }

    public void goToFragment(int id){

        switch (id){
            case R.id.cartFragment:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.homeFragment:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void proceedOrder(){
        String county = orderCountyText.getText().toString().trim();
        String street = orderStreetText.getText().toString().trim();
        String village = orderVillageText.getText().toString().trim();
        String userName = orderUserNameText.getText().toString().trim();
        Float totalPrice = 0f;
        if (validateOrderForm(county,street,village)){
            hideFields(false);
            String priceText = orderPriceText.getText().toString();
            totalPrice = Float.parseFloat(priceText.substring(0,priceText.length()-2));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateAndTime = sdf.format(new Date());
            Order order = new Order(county,street,village,userName, currentDateAndTime, totalPrice);
            for (int i = 0; i<cartItems.size(); i++)
            {
                databaseReference.child("orders").child("order-" + currentDateAndTime).child("cart").push().setValue(cartItems.get(i));
            }
            databaseReference.child("orders").child("order-" + currentDateAndTime).child("details").setValue(order).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                databaseReference.child("cart").removeValue();
                                setDialog(true);
                                AlertDialog alert = dialog.create();
                                orderProgressBar.setVisibility(View.GONE);
                                orderProceedBtn.setVisibility(View.GONE);
                                alert.show();
                            }
                            else{
                                setDialog(false);
                                AlertDialog alert = dialog.create();
                                alert.show();
                                hideFields(true);
                            }
                        }
                    });
        }

    }



    public void getCartItems(){
        totalPrice = 0f;
        databaseReference.child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    CartItem cartItem = new CartItem(
                            snap.child("name").getValue().toString(),
                            snap.child("imageLink").getValue().toString(),
                            Integer.parseInt(snap.child("quantity").getValue().toString()),
                            Float.parseFloat(snap.child("price").getValue().toString())
                            );
                    totalPrice += cartItem.getPrice() * cartItem.getQuantity();
                    cartItems.add(cartItem);
                }
                orderPriceText.setText(String.valueOf(totalPrice) + " €");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public boolean validateOrderForm(String county, String street, String village){

        if (county.isEmpty()){
            orderCountyText.setError("County is required!");
            orderCountyText.requestFocus();
            return false;
        }

        if (county.length() < 4){
            orderCountyText.setError("The county must be at least 4 characters long!");
            orderCountyText.requestFocus();
            return false;
        }

        if (street.isEmpty()){
            orderStreetText.setError("County is required!");
            orderStreetText.requestFocus();
            return false;
        }

        if (street.length()< 4){
            orderStreetText.setError("The street must be at least 4 characters long!");
            orderStreetText.requestFocus();
            return false;
        }

        if (village.isEmpty()){
            orderVillageText.setError("County is required!");
            orderVillageText.requestFocus();
            return false;
        }

        if (village.length() < 4){
            orderVillageText.setError("The village must be at least 4 characters long!");
            orderVillageText.requestFocus();
            return false;
        }

        return true;
    }

    public void hideFields(boolean x){
        if (x)
        {
            orderProgressBar.setVisibility(View.GONE);
            orderUserNameText.setVisibility(View.VISIBLE);
            orderCountyText.setVisibility(View.VISIBLE);
            orderStreetText.setVisibility(View.VISIBLE);
            orderVillageText.setVisibility(View.VISIBLE);
            orderPriceText.setVisibility(View.VISIBLE);
            orderProceedBtn.setClickable(true);

        }
        else{
            orderCountyText.setVisibility(View.GONE);
            orderUserNameText.setVisibility(View.GONE);
            orderStreetText.setVisibility(View.GONE);
            orderVillageText.setVisibility(View.GONE);
            orderProgressBar.setVisibility(View.VISIBLE);
            orderPriceText.setVisibility(View.GONE);
            orderProceedBtn.setClickable(false);

        }
    }


}