package com.example.shop_online.cart;

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

import com.example.shop_online.order.OrderActivity;
import com.example.shop_online.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private TextView totalPriceText, cartEmptyText;
    private Button placeOrderBtn, removeAllBtn;
    private ImageView cartEmptyImage;
    private DatabaseReference databaseReference;


    private float totalPrice;

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
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        totalPriceText = view.findViewById(R.id.totalCartPrice);
        placeOrderBtn = view.findViewById(R.id.placeOrderButton);
        removeAllBtn = view.findViewById(R.id.removeAllButton);
        removeAllBtn.setVisibility(View.GONE);
        removeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            removeCartItemFromDatabase(0,2,0);
            }
        });
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });
        calculateTotalPriceOfCartAndSetText();

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        cartEmptyImage = view.findViewById(R.id.imageCartEmpty);
        cartEmptyText = view.findViewById(R.id.textCartEmpty);





        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        Query query = databaseReference.child("cart");

        // Get data from database for how many items are on current screen
        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>()
                        .setQuery(query, snapshot -> {
                            String cartItemName = snapshot.child("name").getValue().toString();
                            String cartItemImageLink = snapshot.child("imageLink").getValue().toString();
                            int cartItemQuantity = Integer.parseInt(snapshot.child("quantity").getValue().toString());
                            float cartItemPrice = Float.parseFloat(snapshot.child("price").getValue().toString());
                            CartItem cartItem = new CartItem(cartItemName, cartItemImageLink, cartItemQuantity, cartItemPrice);
                            cartItem.setIndex(Integer.parseInt(snapshot.child("index").getValue().toString()));
                            return cartItem;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<CartItem, CartViewHolder>(options) {

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() == 0) {
                    removeAllBtn.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    totalPriceText.setVisibility(View.GONE);
                    placeOrderBtn.setVisibility(View.GONE);
                    cartEmptyText.setVisibility(View.VISIBLE);
                    cartEmptyImage.setVisibility(View.VISIBLE);

                }
                    else {
                        cartEmptyText.setVisibility(View.GONE);
                        cartEmptyImage.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        totalPriceText.setVisibility(View.VISIBLE);
                        placeOrderBtn.setVisibility(View.VISIBLE);
                        removeAllBtn.setVisibility(View.VISIBLE);
                    }
            }

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
                Picasso.get().load(model.getImageLink()).resize(80, 130).into(holder.getCartImage());

                holder.getAddBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.incrementQuantity();
                        removeCartItemFromDatabase(model.getIndex(), 0, holder.getQuantityItem());
                        calculateTotalPriceOfCartAndSetText();
                    }
                });

                holder.getMinusBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.decrementQuantity();
                        removeCartItemFromDatabase(model.getIndex(), 0, holder.getQuantityItem());
                        calculateTotalPriceOfCartAndSetText();
                    }
                });

                holder.getDeleteBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeCartItemFromDatabase(model.getIndex(), 1, holder.getQuantityItem());
                        calculateTotalPriceOfCartAndSetText();
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

    private void removeCartItemFromDatabase(int modelPosition, int changeItem, int quantityItem){
        switch (changeItem) {
            case 0:
                // increment quantity
                databaseReference.child("cart").child(String.valueOf(modelPosition)).child("quantity").setValue(quantityItem);
                break;
            case 1:
                // remove one cart item
                databaseReference.child("cart").child(String.valueOf(modelPosition)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("data", "Remove successfully");
                        }
                        else {
                            Log.i("data", "Remove failed");
                        }
                    }
                });
                break;
            case 2:
                // remove all cart items
                databaseReference.child("cart").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("data", "Remove all cart items");
                        }
                        else {
                            Log.i("data", "Remove cart failed");
                        }
                    }
                });
                break;
        }
    }


    public void calculateTotalPriceOfCartAndSetText(){
        totalPrice = 0;
        databaseReference.child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot x : snapshot.getChildren()){
                    int quantity = Integer.parseInt(x.child("quantity").getValue().toString());
                    totalPrice += (Float.parseFloat(x.child("price").getValue().toString()) * quantity);
                }
                totalPriceText.setText(String.valueOf(totalPrice) + " €");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}