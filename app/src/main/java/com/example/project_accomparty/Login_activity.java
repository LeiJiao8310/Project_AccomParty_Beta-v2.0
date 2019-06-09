package com.example.project_accomparty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Login_activity extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    ProgressBar loginProgress;
    private EditText loginEmail,loginPass;
    //private FirebaseAuth signin_auth;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String TAG = "Login_activity";
    private final static int LOGIN_PERMISSION =1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        login=findViewById(R.id.purpleLogin);
        loginProgress=findViewById(R.id.login_progress);
        loginEmail=findViewById(R.id.login_email);
        loginPass=findViewById(R.id.login_password);


        /**signin_auth = FirebaseAuth.getInstance();

        if (signin_auth.getCurrentUser() != null) {
            startActivity(new Intent(Login_activity.this, Map_activity.class));
            finish();
        }
        setContentView(R.layout.activity_login_activity);**/
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            Intent i = new Intent(getApplicationContext(), ListOnline.class);
            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
            startActivity(i);
        }
        progressDialog = new ProgressDialog(this);















    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void userLogin() {
        String email = loginEmail.getText().toString().trim();
        String password  = loginPass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            loginEmail.setError("Email cannot be empty");
            loginEmail.requestFocus();
           // Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
           // Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            loginPass.setError("Password cannot be empty");
            loginPass.requestFocus();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        //progressDialog.setMessage("Signing in. Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            progressDialog.setMessage("Signing in. Please Wait...");
                            //start the profile activity
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            //updateUI(user);
                            //finish();
                           Intent i = new Intent(Login_activity.this, ListOnline.class);
                           i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                           startActivity(i);
                            //startActivity(new Intent(Login_activity.this, Map_activity.class));
                        }
                        else{
                            try {
                                throw task.getException();
                            }  catch(FirebaseAuthInvalidCredentialsException e) {
                                loginEmail.setError("Invalid Email");
                                loginEmail.requestFocus();
                            }  catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == login){
            userLogin();
        }

        /**if(view == textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }**/
    }



}
