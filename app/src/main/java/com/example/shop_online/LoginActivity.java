package com.example.shop_online;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register, login;
    private TextView textEmail, textPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseBook databaseInit;
    private AlertDialog.Builder dialog;


    private static final String TAG = "Test user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.LoginButton);
        register = findViewById(R.id.RegisterButton);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        textEmail = findViewById(R.id.TextEmailLogin);
        textPassword = findViewById(R.id.TextPassword);

        progressBar = findViewById(R.id.progressBarLogin);
        dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setTitle("Internet problem!");
        dialog.setMessage("Check your internet connection!");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //databaseInit = new DatabaseBook();

    }

    public void initDatabase(){
        new Thread(() -> databaseInit.populateDatabase()).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterButton:
                if (checkInternetConnection())
                {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                else{
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
                break;
            case R.id.LoginButton:
                if (checkInternetConnection())
                {
                    loginUser();
                }
                else{
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
                //initDatabase();
                break;
        }
    }

    public boolean checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }



    public void loginUser(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (validateForm(email, password)){
            hideFields(false);
           mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        hideFields(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{

                        showToastMessageAndHideFields("Login failed! Please check credentials!");
                    }

                });

        }

    }

    // validate form for login fields
    public boolean validateForm(String email, String password){
        // email validating
        if (email.isEmpty()){
            textEmail.setError("Email address is required!");
            textEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Provide valid email!");
            textEmail.requestFocus();
            return false;
        }



        // password validating
        if (password.isEmpty()){
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return false;
        }

        if (password.length() < 6){
            textPassword.setError("The password must be at least 6 characters long!");
            textPassword.requestFocus();
            return false;
        }



        return true;
    }

    private void showToastMessageAndHideFields(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
        hideFields(true);
    }

    public void hideFields (Boolean x){
        if (x)
        {
            progressBar.setVisibility(View.GONE);
            textEmail.setVisibility(View.VISIBLE);
            textPassword.setVisibility(View.VISIBLE);

        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.GONE);
            textPassword.setVisibility(View.GONE);

        }
    }
}