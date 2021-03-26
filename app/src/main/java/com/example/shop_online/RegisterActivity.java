package com.example.shop_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register;
    private TextView textFirstName, textLastName, textEmail, textPassword, textConfirmPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(this);

        textFirstName = findViewById(R.id.TextFirstName);
        textLastName = findViewById(R.id.TextLastName);
        textEmail = findViewById(R.id.TextEmail);
        textPassword = findViewById(R.id.TextPassword);
        textConfirmPassword = findViewById(R.id.TextConfirmPassword);

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.RegisterButton:
                registerUser();
                hideFields(false);
                break;
        }
    }

    private void registerUser() {
        String firstName = textFirstName.getText().toString();
        String lastName = textLastName.getText().toString();
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        String confirmPassword = textConfirmPassword.getText().toString();

        if (validateForm(firstName, lastName, email, password, confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                User user = new User(firstName,lastName,email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "User has been registered!", Toast.LENGTH_LONG).show();
                                            hideFields(true);
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            hideFields(true);
                                        }

                                    }
                                });
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                hideFields(true);
                            }
                        }
                    });
        }
    }

    // validate function for all register fields
    private boolean validateForm (String firstName, String lastName, String email, String password, String confirmPassword){
        Pattern pattern;
        final String NAME_PATTERN = "[ .a-zA-Z]+";
        pattern = Pattern.compile(NAME_PATTERN);


        // first name validating
        if (!pattern.matcher(firstName).matches()){
            textFirstName.setError("Provide valid first name! (a-zA-Z, .)");
            textFirstName.requestFocus();
            return false;
        }

        if (firstName.length() < 5){
            textFirstName.setError("The length of the first name must be at least 5!");
            textFirstName.requestFocus();
            return false;
        }

        if (firstName.isEmpty()){
            textFirstName.setError("First name is required!");
            textFirstName.requestFocus();
            return false;
        }

        // last name validating
        if (!pattern.matcher(lastName).matches()){
            textLastName.setError("Provide valid last name! (a-zA-Z, .)");
            textLastName.requestFocus();
            return false;
        }

        if (lastName.length() < 3){
            textLastName.setError("The length of the last name must be at least 3!");
            textLastName.requestFocus();
            return false;
        }

        if (lastName.isEmpty()){
            textLastName.setError("Last name is required!");
            textLastName.requestFocus();
            return false;
        }

        // email validating
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Provide valid email!");
            textEmail.requestFocus();
            return false;
        }

        if (email.isEmpty()){
            textEmail.setError("Email address is required!");
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

        if (confirmPassword.isEmpty()){
            textConfirmPassword.setError("Confirm password is required!");
            textConfirmPassword.requestFocus();
            return false;
        }

        if (!confirmPassword.equals(password)){
            textConfirmPassword.setError("Confirm password does not match with the password!");
            textConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }


    public void hideFields (Boolean x){
        if (x)
        {
            progressBar.setVisibility(View.GONE);
            textFirstName.setVisibility(View.VISIBLE);
            textLastName.setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.VISIBLE);
            textPassword.setVisibility(View.VISIBLE);
            textConfirmPassword.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            textFirstName.setVisibility(View.GONE);
            textLastName.setVisibility(View.GONE);
            textEmail.setVisibility(View.GONE);
            textPassword.setVisibility(View.GONE);
            textConfirmPassword.setVisibility(View.GONE);
        }
    }
}