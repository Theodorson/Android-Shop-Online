package com.example.shop_online;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{
    private DatabaseReference database;
    private ArrayList<Book> books;
    private static final String TAG = "Test database";

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
        books = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        retrieveDataFromDatabaseAndUpdateUI(view);
        Log.i(TAG, "Home Fragment");
        return view;
    }




    public void retrieveDataFromDatabaseAndUpdateUI(View view){
          Runnable runnable = new Runnable() {
              @Override
              public void run() {
                  database.child("books").addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          for (DataSnapshot ds : snapshot.getChildren()) {
                              String bookName = ds.child("name").getValue().toString();
                              String bookAuthor = ds.child("author").getValue().toString();
                              String bookPublisher = ds.child("publisher").getValue().toString();
                              String bookPublicationDate = ds.child("publicationDate").getValue().toString();
                              String bookDescription = ds.child("description").getValue().toString();
                              String bookImageLink = ds.child("imageLink").getValue().toString();
                              String bookLanguage = ds.child("language").getValue().toString();
                              int bookPages = Integer.parseInt(ds.child("pages").getValue().toString());
                              float bookPrice = Float.parseFloat(ds.child("price").getValue().toString());
                              Book dataBook = new Book(bookName, bookAuthor, bookPublisher, bookLanguage, bookPublicationDate, bookDescription, bookImageLink, bookPages, bookPrice);
                              books.add(dataBook);

                              Log.i(TAG, "retrieve data");
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });
                  requireActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          BookAdapter adapter = new BookAdapter(books);
                          RecyclerView rv = view.findViewById(R.id.recyclerView);
                          rv.setLayoutManager(new LinearLayoutManager(getContext()));
                          rv.setAdapter(adapter);
                      }
                  });
              }
          };
            Executors.newSingleThreadScheduledExecutor().execute(runnable);
    }


}