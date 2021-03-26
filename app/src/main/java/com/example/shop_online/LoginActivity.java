package com.example.shop_online;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       register = findViewById(R.id.RegisterButton);
       register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.LoginButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}