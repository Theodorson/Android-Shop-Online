package com.example.shop_online;

import android.app.Application;
import android.content.Intent;

import com.example.shop_online.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Session extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser userInstance = mAuth.getCurrentUser();
        // test if exist current user or is null to Firebase
        if (userInstance != null){
            Intent intent = new Intent(Session.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Intent i = new Intent(Session.this, LoginActivity.class);
            startActivity(i);
        }

    }





}
