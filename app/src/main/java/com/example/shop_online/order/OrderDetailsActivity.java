package com.example.shop_online.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop_online.R;
import com.example.shop_online.cart.CartItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ConstraintLayout constraintLayout;
    private TextView orderNameTitleText, usernameOrderText, countyOrderTet,
            villageOrderText, streetOrderText, dateOrderText, priceOrderText;
    private Button showCartBtn, showDetailsBtn;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        orderNameTitleText = findViewById(R.id.orderTitleNameText);
        usernameOrderText = findViewById(R.id.userNameOrderDetailsText);
        countyOrderTet = findViewById(R.id.countyOrderDetailsText);
        villageOrderText = findViewById(R.id.villageOrderDetailsText);
        streetOrderText = findViewById(R.id.streetOrderDetailsText);
        dateOrderText = findViewById(R.id.dateOrderDetailsText);
        priceOrderText = findViewById(R.id.priceOrderDetailsText);

        showCartBtn = findViewById(R.id.showOrderCartButton);
        showDetailsBtn = findViewById(R.id.showOrderDetailsButton);

        recyclerView = findViewById(R.id.recyclerViewCartOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        constraintLayout = findViewById(R.id.constraintLayoutOrderDetails);

        showCartBtn.setOnClickListener(this);
        showDetailsBtn.setOnClickListener(this);


        initTexts();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.showOrderCartButton:
                showCart();
                break;
            case R.id.showOrderDetailsButton:
                showDetails();
                break;
        }
    }

    public void initTexts(){
        constraintLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        databaseReference.child(mAuth.getUid()).child("orders").child(id).child("details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            orderNameTitleText.setText(snapshot.child("id").getValue().toString());
            usernameOrderText.setText(snapshot.child("userName").getValue().toString());
            countyOrderTet.setText(snapshot.child("county").getValue().toString());
            villageOrderText.setText(snapshot.child("village").getValue().toString());
            streetOrderText.setText(snapshot.child("street").getValue().toString());
            String date = snapshot.child("date").getValue().toString();
            date = date.substring(6,8) + "/" +  date.substring(4,6) + "/"  + date.substring(0,4);
            dateOrderText.setText(date);
            priceOrderText.setText(snapshot.child("price").getValue().toString() + " €") ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDetails(){

        if (constraintLayout.getVisibility() == View.GONE){
            recyclerView.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
            showCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_more, 0);
            showCartBtn.setBackgroundColor(getColor(R.color.purple_700));
            showDetailsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_less_24, 0);
            showDetailsBtn.setBackgroundColor(getColor(R.color.black));
        }
        else{
            showDetailsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_more, 0);
            showDetailsBtn.setBackgroundColor(getColor(R.color.purple_700));
            constraintLayout.setVisibility(View.GONE);
        }
    }

    public void showCart(){
        if (recyclerView.getVisibility() == View.GONE){
            constraintLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showDetailsBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_more, 0);
            showDetailsBtn.setBackgroundColor(getColor(R.color.purple_700));
            showCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_less_24, 0);
            showCartBtn.setBackgroundColor(getColor(R.color.black));

            onStart();
        }
        else{
            showCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0 ,R.drawable.ic_baseline_expand_more, 0);
            showCartBtn.setBackgroundColor(getColor(R.color.purple_700));
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        Query query = databaseReference.child(mAuth.getUid())
                .child("orders").child(id).child("cart");

        // Get data from database for how many items are on current screen
        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>()
                        .setQuery(query, snapshot -> {
                            String name = snapshot.child("name").getValue().toString();
                            String imageLink = snapshot.child("imageLink").getValue().toString();
                            int quantity = Integer.parseInt(snapshot.child("quantity").getValue().toString());
                            float price = Float.parseFloat(snapshot.child("price").getValue().toString());
                            int index = Integer.parseInt(snapshot.child("index").getValue().toString());

                            CartItem cartItem = new CartItem(name, imageLink, quantity, price);
                            cartItem.setIndex(index);
                            return cartItem;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<CartItem, OrderCartViewHolder>(options) {

            @NonNull
            @Override
            public  OrderCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.order_cart_item, viewGroup,false);

                OrderCartViewHolder orderCartViewHolder = new  OrderCartViewHolder(view);
                return orderCartViewHolder;
            }

            // bind data to list_item
            @Override
            protected void onBindViewHolder(@NonNull  OrderCartViewHolder holder, int position, @NonNull CartItem model) {
                holder.setTextName(model.getName());
                holder.setTextQuantity(String.valueOf(model.getQuantity()));
                holder.setTextPrice(String.valueOf(model.getPrice()));
                Picasso.get().load(model.getImageLink()).resize(80,130).into(holder.getImageView());
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}