package com.example.foundy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foundy.Structures.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    private TextView mPassword;
    private TextView mUsername;
    private TextView mEmail;
    private TextView mNumber;
    private Button mSignUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mPassword = findViewById(R.id.uPassword);
        mUsername = findViewById(R.id.uName);
        mEmail = findViewById(R.id.uEmail);
        mNumber = findViewById(R.id.uPhone);

        mSignUp = findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = mEmail.getText().toString();
                String textUsername = mUsername.getText().toString();
                String intNumber = mNumber.getText().toString();
                String textPassword = mPassword.getText().toString();

                validateInput(textEmail, textUsername, textPassword, intNumber);

            }
        });

    }
    public void validateInput (String valEmail, String valUsername, String valPassword,String valNumber){

        if(valUsername.isEmpty())
        {
            mUsername.setError("Username required");
            mUsername.requestFocus();
            return;
        }

        if (valEmail.isEmpty()) {
            mEmail.setError("Email required");
            mEmail.requestFocus();
            return;
        }

        if(!(Patterns.EMAIL_ADDRESS.matcher(valEmail).matches())){
            mEmail.setError("Not a valid email");
            mEmail.requestFocus();
            return;
        }

        if(valPassword.length() < 6){
            mPassword.setError("Password too short");
            mPassword.requestFocus();
            return;
        }

        if(valNumber.length() != 10)
        {
            mNumber.setError("Invalid Phone");
            mNumber.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(valEmail,valPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.e("Signup", "Successfully signed up user");
                            User user = new User(valEmail, valUsername, valNumber, valPassword);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.e("Signup", "Successfully sent email to user");
                                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignUp.this, "User has been registered!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                Toast.makeText(SignUp.this, "User has been registered!", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(SignUp.this, "User has NOT been registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(SignUp.this, "User has NOT been registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
