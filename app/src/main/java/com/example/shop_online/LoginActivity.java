package com.example.shop_online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register, login;
    private TextView textEmail, textPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

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

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.LoginButton:
                loginUser();
                break;
        }
    }

    public void loginUser(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (validateForm(email, password)){
            hideFields(false);
           mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Log.i(TAG,"Login successsful!");
                        hideFields(true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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



        // password and confirm password validating
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