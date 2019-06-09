package com.example.project_accomparty;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup_activity extends AppCompatActivity {

    Button gosignup,goClearAll;
    ;
    EditText signupEmail, signuppass, signupusername,signupPhoneNum,signupCityName;
    ProgressBar signupbar;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabse;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);


        signupEmail = findViewById(R.id.signup_email);
        signuppass = findViewById(R.id.signup_password);
        signupusername = findViewById(R.id.signup_username);
        signupPhoneNum=findViewById(R.id.signup_phone);
        signupCityName=findViewById(R.id.signup_cityName);
        signupbar = findViewById(R.id.signup_progressBar);
        signupbar.setVisibility(View.GONE);
        gosignup = findViewById(R.id.signup_signupBut);
        goClearAll = findViewById(R.id.signup_clear);

        databaseReference = firebaseDatabse.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        goClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupEmail.getText().clear();
                signuppass.getText().clear();
                signupusername.getText().clear();
                signupPhoneNum.getText().clear();
                signupCityName.getText().clear();
                signupusername.requestFocus();
            }
        });

        gosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = signupusername.getText().toString();
                final String email = signupEmail.getText().toString();
                final String phoneNum =signupPhoneNum.getText().toString() ;
                final String cityName = signupCityName.getText().toString();
                String password = signuppass.getText().toString();



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Signup_activity.this, "Email must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Signup_activity.this, "Username must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup_activity.this, "Password must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(Signup_activity.this, "Phone Number must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_phone).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cityName)) {
                    Toast.makeText(Signup_activity.this, "City Name must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_cityName).requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup_activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Users information = new Users(username, email,phoneNum,cityName,FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Signup_activity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                            //firebaseAuth.getInstance().signOut();
                                            Intent i = new Intent(getBaseContext(), ListOnline.class);
                                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                            startActivity(i);


                                        }

                                    });
                                }
                            }
                        });


            }
        });
    }
}

















        /** signup_signupBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString().trim();
                String password = signuppass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup_activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Signup_activity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Signup_activity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Signup_activity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });**/



