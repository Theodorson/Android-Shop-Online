package com.example.shop_online.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_online.MainActivity;
import com.example.shop_online.R;
import com.example.shop_online.book.Book;
import com.example.shop_online.book.BookItemActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class NewBookActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private TextView textName, textAuthor, textPublisher, textPublicationDate,
                     textLanguage, textPages, textNrOfCopies, textLinkImage,
                     textDescription, textPrice, textTitle;
    private Button addNewBookBtn, backButton;
    private ProgressBar newBookProgressBar;
    private ScrollView scrollView;

    private Intent intent;
    private int year, month, day, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        textName = findViewById(R.id.textViewNewBookName);
        textAuthor = findViewById(R.id.textViewNewBookAuthor);
        textPublisher = findViewById(R.id.textViewNewBookPublisher);
        textPublicationDate = findViewById(R.id.textViewNewBookPublicationDate);
        textLanguage = findViewById(R.id.textViewNewBookLanguage);
        textPages = findViewById(R.id.textViewNewBookPages);
        textNrOfCopies = findViewById(R.id.textViewNewBookNrOfCopies);
        textLinkImage = findViewById(R.id.textViewNewBookLinkImage);
        textDescription = findViewById(R.id.textViewNewBookDescription);
        textPrice = findViewById(R.id.textViewNewBookPrice);
        textTitle = findViewById(R.id.textViewTitle);

        addNewBookBtn = findViewById(R.id.addNewBookButton);
        backButton = findViewById(R.id.backManagementButton);
        newBookProgressBar = findViewById(R.id.progressBarNewBook);
        scrollView = findViewById(R.id.scrollViewNewBook);
        newBookProgressBar.setVisibility(View.GONE);

        addNewBookBtn.setOnClickListener(this);
        backButton.setOnClickListener(this);
        textPublicationDate.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        intent = getIntent();
        setForNewOrUpdateBook();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewBookButton:
                addNewBookOrUpdate();
                break;
            case R.id.textViewNewBookPublicationDate:
                getPublicationDate();
                break;
            case R.id.backManagementButton:
                if (intent.getExtras().getInt("activity") == 1) {
                    Intent intent2 = new Intent(this, BookItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("book id", id);
                    bundle.putInt("book nr model", Integer.parseInt(textNrOfCopies.getText().toString()));
                    bundle.putString("book price", textPrice.getText().toString());
                    bundle.putString("book pages", textPages.getText().toString());
                    bundle.putString("book name", textName.getText().toString());
                    bundle.putString("book language", textLanguage.getText().toString());
                    bundle.putString("book author", textAuthor.getText().toString());
                    bundle.putString("book publisher",textPublisher.getText().toString());
                    bundle.putString("book publication date", textPublicationDate.getText().toString());
                    bundle.putString("book image link", textLinkImage.getText().toString());
                    bundle.putString("book description", textDescription.getText().toString());
                    intent2.putExtras(bundle);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                }
                else {
                    Intent intent1 = new Intent(this, ManagementActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
                break;
        }
    }


    public void setForNewOrUpdateBook(){
        switch (intent.getExtras().getInt("activity"))
        {
            case 1:
                id = intent.getExtras().getInt("id");
                setTextsAndButtons(1);
                break;
            case 2:
                setTextsAndButtons(2);
                break;
        }

    }

    public void setTextsAndButtons(int decision){
        if (decision == 2){
            addNewBookBtn.setText("ADD");
            textTitle.setText("NEW BOOK");
        }
        else{
            databaseReference.child("books").child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    textTitle.setText("UPDATE BOOK");

                    Log.i("TAG", String.valueOf(snapshot.getChildrenCount()));

                    textName.setText(snapshot.child("name").getValue().toString());
                    textAuthor.setText(snapshot.child("author").getValue().toString());
                    textPublisher.setText(snapshot.child("publisher").getValue().toString());
                    textPublicationDate.setText(snapshot.child("publicationDate").getValue().toString());
                    textLanguage.setText(snapshot.child("language").getValue().toString());
                    textPages.setText(snapshot.child("pages").getValue().toString());
                    textLinkImage.setText(snapshot.child("imageLink").getValue().toString());
                    textDescription.setText(snapshot.child("description").getValue().toString());
                    textPrice.setText(snapshot.child("price").getValue().toString());
                    textNrOfCopies.setText(snapshot.child("nrOfModel").getValue().toString());

                    addNewBookBtn.setText("UPDATE");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


    public void addNewBookOrUpdate(){
        String nameStr = textName.getText().toString().trim();
        String authorStr = textAuthor.getText().toString().trim();
        String publisherStr = textPublisher.getText().toString().trim();
        String publicationDateStr = textPublicationDate.getText().toString().trim();
        String languageStr = textLanguage.getText().toString().trim();
        String pagesStr = textPages.getText().toString().trim();
        String nrOfCopiesStr = textNrOfCopies.getText().toString().trim();
        String linkImageStr = textLinkImage.getText().toString().trim();
        String descriptionStr = textDescription.getText().toString().trim();
        String priceStr = textPrice.getText().toString().trim();

        if (intent.getExtras().getInt("activity") == 2) {
            if (validateFormBook(nameStr, authorStr, publisherStr, publicationDateStr, languageStr,
                    pagesStr, nrOfCopiesStr, linkImageStr, descriptionStr, priceStr)) {
                Log.i("TAG", "Add new book");

                scrollView.setVisibility(View.GONE);
                newBookProgressBar.setVisibility(View.VISIBLE);
                databaseReference.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int newBookID = Integer.parseInt(String.valueOf(snapshot.getChildrenCount())) + 1;
                        int pages = Integer.parseInt(pagesStr);
                        int nrOfCopies = Integer.parseInt(nrOfCopiesStr);
                        float price = Float.parseFloat(priceStr);
                        Book book = new Book(nameStr, authorStr, publisherStr, languageStr,
                                publicationDateStr, descriptionStr, linkImageStr,
                                pages, nrOfCopies, price);
                        book.setId(newBookID);
                        databaseReference.child("books").child(String.valueOf(newBookID)).setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
                                    newBookProgressBar.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);

                                    //TODO implements back to HomeFragment

                                    Intent intent1 = new Intent(NewBookActivity.this, MainActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent1);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Book added failed!", Toast.LENGTH_SHORT).show();
                                    newBookProgressBar.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else{
            if (validateFormBook(nameStr, authorStr, publisherStr, publicationDateStr, languageStr,
                    pagesStr, nrOfCopiesStr, linkImageStr, descriptionStr, priceStr)) {
                Log.i("TAG", "Update book");

                scrollView.setVisibility(View.GONE);
                newBookProgressBar.setVisibility(View.VISIBLE);


                databaseReference.child("books").child(String.valueOf(id)).child("name").setValue(nameStr);
                databaseReference.child("books").child(String.valueOf(id)).child("author").setValue(authorStr);
                databaseReference.child("books").child(String.valueOf(id)).child("publisher").setValue(publisherStr);
                databaseReference.child("books").child(String.valueOf(id)).child("publicationDate").setValue(publicationDateStr);
                databaseReference.child("books").child(String.valueOf(id)).child("language").setValue(languageStr);
                databaseReference.child("books").child(String.valueOf(id)).child("pages").setValue(Integer.parseInt(pagesStr));
                databaseReference.child("books").child(String.valueOf(id)).child("price").setValue(Integer.parseInt(priceStr));
                databaseReference.child("books").child(String.valueOf(id)).child("imageLink").setValue(linkImageStr);
                databaseReference.child("books").child(String.valueOf(id)).child("description").setValue(descriptionStr);
                databaseReference.child("books").child(String.valueOf(id)).child("nrOfModel").setValue(Integer.parseInt(nrOfCopiesStr));

                Toast.makeText(getApplicationContext(), "Book updated successfully", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("book id", id);
                bundle.putInt("book nr model", Integer.parseInt(nrOfCopiesStr));
                bundle.putString("book price", priceStr);
                bundle.putString("book language", languageStr);
                bundle.putString("book pages", pagesStr);
                bundle.putString("book name", nameStr);
                bundle.putString("book author", authorStr);
                bundle.putString("book publisher", publisherStr);
                bundle.putString("book publication date", publicationDateStr);
                bundle.putString("book image link", linkImageStr);
                bundle.putString("book description", descriptionStr);

                Intent intent2 = new Intent(NewBookActivity.this, BookItemActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                }

        }

    }


    public void getPublicationDate(){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String monthStr = getMonth(monthOfYear);
                        String dayStr = "";
                        if (dayOfMonth < 10){
                            dayStr = "0" + String.valueOf(dayOfMonth);
                        }
                        else{
                            dayStr = String.valueOf(dayOfMonth);
                        }
                        textPublicationDate.setText(dayStr + "/" + monthStr + "/" + year);

                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    public String getMonth(int month){
        switch (month){
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "Jul";
            case 7: return "Aug";
            case 8: return "Sept";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
        }
        return "?";
    }


    public boolean validateFormBook(String nameStr, String authorStr, String publisherStr, String publicationDateStr,
                                    String languageStr, String pagesStr, String nrOfCopiesStr, String linkImageStr,
                                    String descriptionStr, String priceStr){


        if (nameStr.isEmpty()){
            textName.setError("Name is required!");
            textName.requestFocus();
            return false;
        }

        if (nameStr.length() < 5){
            textName.setError("The length of the name must be at least 5!");
            textName.requestFocus();
            return false;
        }

        if (authorStr.isEmpty()){
            textAuthor.setError("Author's name is required!");
            textAuthor.requestFocus();
            return false;
        }

        if (authorStr.length() < 5){
            textAuthor.setError("The length of the author's name must be at least 5!");
            textAuthor.requestFocus();
            return false;
        }

        if (publisherStr.isEmpty()){
            textPublisher.setError("Publisher is required!");
            textPublisher.requestFocus();
            return false;
        }

        if (publisherStr.length() < 5){
            textPublisher.setError("The length of the publisher must be at least 5!");
            textPublisher.requestFocus();
            return false;
        }

        if (publicationDateStr.isEmpty()){
            textPublicationDate.setError("Publication date is required!");
            textPublicationDate.requestFocus();
            return false;
        }

        if (languageStr.isEmpty()){
            textLanguage.setError("Language is required!");
            textLanguage.requestFocus();
            return false;
        }

        if (languageStr.length() < 5){
            textLanguage.setError("The length of the language must be at least 5!");
            textLanguage.requestFocus();
            return false;
        }

        if (pagesStr.isEmpty()){
            textPages.setError("Number of page is required!");
            textPages.requestFocus();
            return false;
        }

        long pageNr = Long.parseLong(pagesStr);
        if (pageNr <= 10 || pageNr >= 1500 ) {
            textPages.setError("The range of pages must be between 10-1500!");
            textPages.requestFocus();
            return false;
        }


        if (nrOfCopiesStr.isEmpty()){
            textNrOfCopies.setError("Number of copies is required!");
            textNrOfCopies.requestFocus();
            return false;
        }

        long nrOfCopies = Long.parseLong(nrOfCopiesStr);
        if (nrOfCopies <= 0 || nrOfCopies >= 200){
            textNrOfCopies.setError("The range of pages must be between 1-200!");
            textNrOfCopies.requestFocus();
            return false;
        }


        if (linkImageStr.isEmpty()){
            textLinkImage.setError("Link image is required!");
            textLinkImage.requestFocus();
            return false;
        }

        if(!URLUtil.isValidUrl(linkImageStr)) {
            textLinkImage.setError("The link image is not valid!");
            textLinkImage.requestFocus();
            return false;
        }



        if (descriptionStr.isEmpty()){
            textDescription.setError("Description is required!");
            textDescription.requestFocus();
            return false;
        }

        if (descriptionStr.length() < 5){
            textDescription.setError("The length of the description must be at least 5!");
            textDescription.requestFocus();
            return false;
        }

        if (priceStr.isEmpty()){
            textPrice.setError("Price is required!");
            textPrice.requestFocus();
            return false;
        }

        long price = Long.parseLong(priceStr);
        if (price <= 5 || price >= 500){
            textPrice.setError("The range of price must be between 5-500 €!");
            textPrice.requestFocus();
            return false;
        }

        return true;
    }


}
