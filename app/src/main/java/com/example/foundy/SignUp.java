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

public class SignUp extends AppCompatActivity {

    private TextView password;
    private TextView username;
    private TextView email;
    private TextView number;
    private Button signUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        password = findViewById(R.id.uPassword);
        username = findViewById(R.id.uName);
        email = findViewById(R.id.uEmail);
        number = findViewById(R.id.uPhone);

        signUp = findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                String textUsername = username.getText().toString();
                String intNumber = number.getText().toString();
                String textPassword = password.getText().toString();

                validateInput(textEmail, textUsername, textPassword, intNumber);

            }

            public void validateInput (String valEmail, String valUsername, String valPassword,String valNumber){

                if(valUsername.isEmpty())
                {
                    username.setError("Username required");
                    username.requestFocus();
                    return;
                }

                if (valEmail.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }

                if(!(Patterns.EMAIL_ADDRESS.matcher(valEmail).matches())){
                    email.setError("Not a valid email");
                    email.requestFocus();
                    return;
                }

                if(valPassword.length() < 6){
                    password.setError("Password too short");
                    password.requestFocus();
                    return;
                }

                if(valNumber.length() != 10)
                {
                    number.setError("Invalid Phone");
                    number.requestFocus();
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
        });


    }
}
