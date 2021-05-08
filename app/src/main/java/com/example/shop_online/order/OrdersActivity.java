package com.example.shop_online.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop_online.MainActivity;
import com.example.shop_online.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OrdersActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

    private TextView emptyText;
    private Button backBtn;
    private ImageView emptyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());

        emptyText = findViewById(R.id.emptyOrderText);
        emptyText.setVisibility(View.GONE);
        emptyImage = findViewById(R.id.emptyOrderImage);
        emptyImage.setVisibility(View.GONE);
        backBtn = findViewById(R.id.backToShopOrderButton);
        backBtn.setVisibility(View.GONE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycleViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = databaseReference.child("orders");

       // Get data from database for how many items are on current screen
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, snapshot -> {
                                String county = snapshot.child("details").child("county").getValue().toString();
                                String village = snapshot.child("details").child("village").getValue().toString();
                                String street = snapshot.child("details").child("street").getValue().toString();
                                String userName = snapshot.child("details").child("userName").getValue().toString();
                                String date = snapshot.child("details").child("date").getValue().toString();
                                float price = Float.parseFloat(snapshot.child("details").child("price").getValue().toString());
                                Order order = new Order(county, street, village, userName, date, price);
                                return order;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() == 0) {
                    // set visibility off
                    recyclerView.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                    emptyImage.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.VISIBLE);
                }
                else {
                    // set visibility on
                    emptyText.setVisibility(View.GONE);
                    emptyImage.setVisibility(View.GONE);
                    backBtn.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.order_item, viewGroup,false);


                return new OrderViewHolder(view);
            }

            // bind data to list_item
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
                holder.setTextOrderName("Order " + model.getDate());
                holder.setTextOrderPrice(model.getPrice());
                holder.setTextOrderDetails(
                        model.getCounty() + ", " + model.getVillage() + ", " +
                                model.getStreet()
                );


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
}