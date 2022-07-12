package com.example.foundy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        TextView signUp;
        Button signInButton;
        TextInputLayout loginPass;
        TextView loginEmail;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.loginButton);
        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signUpButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUp.class);
                startActivity(i);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPass.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified() == true) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                Log.i("LoginActivity", "Logged in");
                            } else {
                                Log.i("LoginActivity", "Signed out");
                                mAuth.signOut();
                            }
                        }
                    }
                });
            }
        });
    }
}