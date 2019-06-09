package com.example.project_accomparty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static Button loginBut;
    public static Button signupBut;
    FirebaseAuth firebaseAuth;
    //private AlphaAnimation buttonClick = new AlphaAnimation(0.9F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            Intent i = new Intent(getApplicationContext(), ListOnline.class);
            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
            startActivity(i);
        }

        loginBut = findViewById(R.id.loginButton);
        signupBut = findViewById(R.id.signupButton);

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // v.startAnimation(buttonClick);
                openLogin();
            }
        });

        signupBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.startAnimation(buttonClick);
                openSignup();
            }
        });
    }

    public void openLogin(){
        Intent gologin = new Intent(this, Login_activity.class);
        startActivity(gologin);


    }

    public void openSignup(){
        Intent gosinup = new Intent(this, Signup_activity.class);
        startActivity(gosinup);


    }
}
