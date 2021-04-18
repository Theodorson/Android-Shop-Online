package com.example.shop_online;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private FirebaseRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users").child(mAuth.getUid()).child("cart");

        // Get data from database
        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>()
                        .setQuery(query, snapshot -> {
                            String cartItemName = snapshot.child("name").getValue().toString();
                            Log.i("data",cartItemName);
                            String cartItemImageLink = snapshot.child("imageLink").getValue().toString();
                            Log.i("data",cartItemImageLink);
                            int cartItemQuantity = Integer.parseInt(snapshot.child("quantity").getValue().toString());
                            float cartItemPrice = Float.parseFloat(snapshot.child("price").getValue().toString());
                            return new CartItem(cartItemName, cartItemImageLink, cartItemQuantity, cartItemPrice);
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<CartItem, CartViewHolder>(options) {
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.cart_item, viewGroup,false);
                CartViewHolder cartViewHolder = new CartViewHolder(view);


                return cartViewHolder;
            }

            // bind data to list_item
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartItem model) {
                holder.setCartItemName(model.getName());
                holder.setCartItemQuantity(model.getQuantity());
                holder.setCartItemPrice(model.getPrice());
                Picasso.get().load(model.getImageLink()).resize(80,130).into(holder.getCartImage());
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