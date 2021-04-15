package com.example.shop_online;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("books");

        // Get data from database
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, snapshot -> {
                            int bookPages = Integer.parseInt(snapshot.child("pages").getValue().toString());
                            float bookPrice = Float.parseFloat(snapshot.child("price").getValue().toString());
                            String imageLink = snapshot.child("imageLink").getValue().toString();
                            Book book = new Book(
                                    snapshot.child("name").getValue().toString(),
                                    snapshot.child("author").getValue().toString(),
                                    snapshot.child("publisher").getValue().toString(),
                                    snapshot.child("language").getValue().toString(),
                                    snapshot.child("publicationDate").getValue().toString(),
                                    snapshot.child("description").getValue().toString(),
                                    imageLink,
                                    bookPages,
                                    bookPrice
                            );
                            return book;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item, viewGroup,false);
                BookViewHolder bookViewHolder = new BookViewHolder(view);
                bookViewHolder.setOnClickListener(new BookViewHolder.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("On click",String.valueOf(position));
                        Fragment fragment = new Fragment();
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Log.i("On click",String.valueOf(R.id.bookFragment));
                        fragmentTransaction.replace(R.id.bookFragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });

                return bookViewHolder;
            }

            // bind data to list_item
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
                holder.setBookName(model.getName());
                holder.setBookAuthor(model.getAuthor());
                holder.setBookPrice(model.getPrice());
                Picasso.get().load(model.getImageLink()).resize(100,150).into(holder.getImageView());
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


