package com.example.shop_online.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shop_online.R;
import com.example.shop_online.login.User;
import com.example.shop_online.order.Order;
import com.example.shop_online.order.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class AccountsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycleViewAccount);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = databaseReference.child("Users");

        // Get data from database for how many items are on current screen
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, snapshot -> {
                            String lastName = snapshot.child("lastName").getValue().toString();
                            String firstName = snapshot.child("firstName").getValue().toString();
                            String email = snapshot.child("email").getValue().toString();
                            String id = snapshot.child("id").getValue().toString();
                            Boolean admin = Boolean.parseBoolean(snapshot.child("admin").getValue().toString());
                            User user = new User(firstName, lastName, email);
                            user.setAdmin(admin);
                            user.setId(id);
                            return user;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<User, AccountViewHolder>(options) {

            @NonNull
            @Override
            public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.account_item, viewGroup,false);


                return new AccountViewHolder(view);
            }

            // bind data to list_item
            @Override
            protected void onBindViewHolder(@NonNull AccountViewHolder holder, int position, @NonNull User model) {
            holder.setAccountEmail(model.getEmail());
            holder.setAccountName(model.getLastName() + " " + model.getFirstName());
            if (model.isAdmin()){
                holder.setAccountType("Admin");
            }
            else{
                holder.setAccountType("User");
            }
            String path = "users/" + model.getId() + "/profile.jpg";
            Log.i("Database",path);
            storage.getReference(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // image exist
                    Picasso.get().load(uri).fit().into(holder.getAccountImage());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // image not found
                    Log.i("Database","Profil Image not found");
                }
            });
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