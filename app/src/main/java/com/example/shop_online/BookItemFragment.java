package com.example.shop_online;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookItemFragment extends Fragment {

    private ImageView bookImage;
    private TextView bookNameText, bookAuthorText, bookLanguageText, bookDescriptionText, bookPublisherText, bookPublicationDateText, bookPriceText, bookPagesText, bookDetailsText;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookItemFragment newInstance(String param1, String param2) {
        BookItemFragment fragment = new BookItemFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_item, container, false);

        bookImage = view.findViewById(R.id.bookItemImage);
        bookNameText = view.findViewById(R.id.bookItemName);
        bookAuthorText = view.findViewById(R.id.bookAuthorItem);
        bookLanguageText = view.findViewById(R.id.bookLanguageItem);
        bookPublisherText = view.findViewById(R.id.bookPublisherItem);
        bookPublicationDateText = view.findViewById(R.id.bookPublicationDateItem);
        bookPagesText = view.findViewById(R.id.bookPagesItem);
        bookPriceText = view.findViewById(R.id.bookPriceItem);
        bookDescriptionText = view.findViewById(R.id.bookDescriptionItem);
        bookDetailsText = view.findViewById(R.id.bookDetailsItem);
        bookDetailsText.setText("Details");

        return view;
    }
}