package com.example.shop_online;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.shop_online.book.Book;
import com.example.shop_online.book.BookItemActivity;
import com.example.shop_online.book.BookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

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

        // set up appbar
        toolbar = view.findViewById(R.id.appbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);


        // set up recycleView for books
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.appbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.orderOption:
                filter("", 2);
                Toast.makeText(getActivity(), "order by price", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsOption:
                Toast.makeText(getActivity(), "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.searchOption:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText, 1);
                        return false;
                    }
                });
                Toast.makeText(getActivity(), "search", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void filter(String newText, int option) {
        Query query = null;
        switch (option){
            case 1:
                query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("books").orderByChild("name").startAt(newText).endAt(newText+'~');
                break;
            case 2:
                query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("books").orderByChild("price");
                break;
        }


        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, snapshot -> {
                            int bookPages = Integer.parseInt(snapshot.child("pages").getValue().toString());
                            int nrOfModel = Integer.parseInt(snapshot.child("nrOfModel").getValue().toString());
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
                                    nrOfModel,
                                    bookPrice
                            );
                            book.setId(Integer.parseInt(snapshot.child("id").getValue().toString()));
                            return book;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.book_item, viewGroup,false);
                BookViewHolder bookViewHolder = new BookViewHolder(view);
                bookViewHolder.setOnClickListener(new BookViewHolder.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("book name", options.getSnapshots().get(position).getName());
                        bundle.putString("book author", options.getSnapshots().get(position).getAuthor());
                        bundle.putString("book language", options.getSnapshots().get(position).getLanguage());
                        bundle.putString("book publisher", options.getSnapshots().get(position).getPublisher());
                        bundle.putString("book publication date", options.getSnapshots().get(position).getPublicationDate());
                        bundle.putString("book pages", String.valueOf(options.getSnapshots().get(position).getPages()));
                        bundle.putInt("book nr model", options.getSnapshots().get(position).getNrOfModel());
                        bundle.putString("book price", String.valueOf(options.getSnapshots().get(position).getPrice()));
                        bundle.putString("book description", options.getSnapshots().get(position).getDescription());
                        bundle.putString("book image link", options.getSnapshots().get(position).getImageLink());
                        bundle.putInt("book id", options.getSnapshots().get(position).getId());
                        Intent intent = new Intent(getActivity(), BookItemActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

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
    public void onStart() {
        super.onStart();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("books");

        // Get data from database for how many items are on current screen
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, snapshot -> {
                            int bookPages = Integer.parseInt(snapshot.child("pages").getValue().toString());
                            int nrOfModel = Integer.parseInt(snapshot.child("nrOfModel").getValue().toString());
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
                                    nrOfModel,
                                    bookPrice
                            );
                            book.setId(Integer.parseInt(snapshot.child("id").getValue().toString()));
                            return book;
                        })
                        .build();


        // set the adapter
        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.book_item, viewGroup,false);
                BookViewHolder bookViewHolder = new BookViewHolder(view);
                bookViewHolder.setOnClickListener(new BookViewHolder.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("book name", options.getSnapshots().get(position).getName());
                        bundle.putString("book author", options.getSnapshots().get(position).getAuthor());
                        bundle.putString("book language", options.getSnapshots().get(position).getLanguage());
                        bundle.putString("book publisher", options.getSnapshots().get(position).getPublisher());
                        bundle.putString("book publication date", options.getSnapshots().get(position).getPublicationDate());
                        bundle.putString("book pages", String.valueOf(options.getSnapshots().get(position).getPages()));
                        bundle.putInt("book nr model", options.getSnapshots().get(position).getNrOfModel());
                        bundle.putString("book price", String.valueOf(options.getSnapshots().get(position).getPrice()));
                        bundle.putString("book description", options.getSnapshots().get(position).getDescription());
                        bundle.putString("book image link", options.getSnapshots().get(position).getImageLink());
                        bundle.putInt("book id", options.getSnapshots().get(position).getId());
                        Intent intent = new Intent(getActivity(), BookItemActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

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


