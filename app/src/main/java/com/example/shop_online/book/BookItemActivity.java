package com.example.shop_online.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_online.admin.NewBookActivity;
import com.example.shop_online.cart.CartItem;
import com.example.shop_online.MainActivity;
import com.example.shop_online.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookItemActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView bookImage;
    private TextView bookNameText, bookAuthorText, bookLanguageText, bookDescriptionText,
            bookPublisherText, bookPublicationDateText, bookPriceText,
            bookPagesText, bookDetailsText, bookStockText, bookDisableText;
    private Button buttonBack, buttonUpdate, buttonDelete, buttonDisable;
    private FloatingActionButton addToCartButton;


    private float price;
    private int id, nrOfModel;
    private String imageLink;
    private boolean available;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);

        bookImage = findViewById(R.id.bookItemImage);
        bookNameText = findViewById(R.id.bookItemName);
        bookAuthorText = findViewById(R.id.bookAuthorItem);
        bookLanguageText = findViewById(R.id.bookLanguageItem);
        bookPublisherText = findViewById(R.id.bookPublisherItem);
        bookPublicationDateText = findViewById(R.id.bookPublicationDateItem);
        bookPagesText = findViewById(R.id.bookPagesItem);
        bookPriceText = findViewById(R.id.bookPriceItem);
        bookDescriptionText = findViewById(R.id.bookDescriptionItem);
        bookDetailsText = findViewById(R.id.bookDetailsItem);
        bookStockText = findViewById(R.id.textViewEmptyStock);
        bookDisableText = findViewById(R.id.bookItemDisableText);
        bookDetailsText.setText("Details");

        buttonBack = findViewById(R.id.backHomeButton);
        buttonBack.setOnClickListener(this);

        addToCartButton = findViewById(R.id.addToCartFloatingButton);
        addToCartButton.setOnClickListener(this);

        buttonUpdate = findViewById(R.id.updateItemButton);
        buttonUpdate.setOnClickListener(this);

        buttonDelete = findViewById(R.id.deleteItemButton);
        buttonDelete.setOnClickListener(this);

        buttonDisable = findViewById(R.id.disableItemButton);
        buttonDisable.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        initBookItem();
        dialog = new AlertDialog.Builder(BookItemActivity.this);
        setDialog();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.backHomeButton:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.addToCartFloatingButton:
                addToCart();
                break;
            case R.id.updateItemButton:
                goToUpdateActivity();
                break;
            case R.id.deleteItemButton:
                AlertDialog alert = dialog.create();
                alert.show();
                break;
            case R.id.disableItemButton:
                disableBookFromDatabase();
                break;
        }
    }

    public void setDialog(){
        dialog.setTitle("DELETE!");
        dialog.setMessage("Do you really want to delete this book?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBookFromDatabase();
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }


    public void initBookItem(){
        Intent intent = getIntent();
        id = intent.getExtras().getInt("book id");
        available = intent.getExtras().getBoolean("book available");
        nrOfModel = intent.getExtras().getInt("book nr model");
        bookNameText.setText(intent.getExtras().getString("book name"));
        price = Float.parseFloat(intent.getExtras().getString("book price"));
        bookPriceText.setText(intent.getExtras().getString("book price") + "???");
        bookAuthorText.setText("Author: " + intent.getExtras().getString("book author"));
        bookLanguageText.setText("Language: " + intent.getExtras().getString("book language"));
        bookPublisherText.setText("Publisher: " + intent.getExtras().getString("book publisher"));
        bookPublicationDateText.setText("Publication date: " + intent.getExtras().getString("book publication date"));
        bookPagesText.setText("Pages: " + intent.getExtras().getString("book pages"));
        bookDescriptionText.setText("Description: \n" + intent.getExtras().getString("book description"));
        imageLink = intent.getExtras().getString("book image link");
        Picasso.get().load(imageLink).resize(150,180).into(bookImage);


        if (!available){
            bookDisableText.setVisibility(View.VISIBLE);
            buttonDisable.setBackgroundResource(R.drawable.ic_baseline_lock);
            addToCartButton.setVisibility(View.GONE);
        }
        else{
            buttonDisable.setBackgroundResource(R.drawable.ic_baseline_lock_open);
            bookDisableText.setVisibility(View.GONE);
        }


        // test for admin mode
        databaseReference.child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean admin = (Boolean) snapshot.child("admin").getValue();
                if (admin.booleanValue()){
                    buttonUpdate.setVisibility(View.VISIBLE);
                    buttonDelete.setVisibility(View.VISIBLE);
                    buttonDisable.setVisibility(View.VISIBLE);
                    addToCartButton.setVisibility(View.GONE);

                    if (nrOfModel <= 0){
                        bookStockText.setVisibility(View.VISIBLE);
                    }
                    else{
                        bookStockText.setVisibility(View.GONE);
                    }
                }
                else{
                    buttonUpdate.setVisibility(View.GONE);
                    buttonDelete.setVisibility(View.GONE);
                    buttonDisable.setVisibility(View.GONE);

                    if (nrOfModel <= 0){
                        bookStockText.setVisibility(View.VISIBLE);
                        addToCartButton.setVisibility(View.GONE);
                    }
                    else{
                        bookStockText.setVisibility(View.GONE);
                        if (available) {
                            addToCartButton.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToUpdateActivity(){
        Bundle bundle = new Bundle();
        bundle.putInt("activity", 1);
        bundle.putInt("id", id);
        Intent intent = new Intent(this, NewBookActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deleteBookFromDatabase(){

        databaseReference.child("books").child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Book deleted successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BookItemActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Book deleted failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void disableBookFromDatabase(){

        if (available){
            databaseReference.child("books").child(String.valueOf(id)).child("available").setValue(false);
            available = false;
            bookDisableText.setVisibility(View.VISIBLE);
            buttonDisable.setBackgroundResource(R.drawable.ic_baseline_lock);
        }
        else{
            databaseReference.child("books").child(String.valueOf(id)).child("available").setValue(true);
            available = true;
            bookDisableText.setVisibility(View.GONE);
            buttonDisable.setBackgroundResource(R.drawable.ic_baseline_lock_open);
        }


    }


    public void addToCart() {
        databaseReference.child("Users").child(mAuth.getUid()).child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(String.valueOf(id))) {
                    Toast.makeText(getApplicationContext(), "Product already in your cart!", Toast.LENGTH_SHORT).show();
                }
                else{

                    CartItem cartItem = new CartItem(bookNameText.getText().toString(), imageLink, 1, price);
                    cartItem.setIndex(id);


                    databaseReference.child("Users").child(mAuth.getUid()).child("cart").child(String.valueOf(id)).setValue(cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Product add to cart succesfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Product add to cart failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}