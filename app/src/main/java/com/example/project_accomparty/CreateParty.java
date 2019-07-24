package com.example.project_accomparty;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class CreateParty extends AppCompatActivity {
    final String TAG = "CreateParty";
    Button create, clear;
    Switch split;
    EditText name, address, type, description, cost;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        create = findViewById(R.id.np_create);
        clear = findViewById(R.id.np_clear);
        split = findViewById(R.id.np_split);
        name = findViewById(R.id.np_name);
        address = findViewById(R.id.np_address);
        type = findViewById(R.id.np_type);
        description = findViewById(R.id.np_description);
        cost = findViewById(R.id.np_cost);
        cost.setVisibility(View.INVISIBLE);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Party");

        split.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    cost.setVisibility(View.VISIBLE);
                else
                    cost.setVisibility(View.INVISIBLE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.getText().clear();
                address.getText().clear();
                type.getText().clear();
                description.getText().clear();
                cost.getText().clear();
                name.requestFocus();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Party information;
                final String s_name = name.getText().toString();
                final String s_address = address.getText().toString();
                final String s_type = type.getText().toString() ;
                final String s_description = description.getText().toString();
                final String s_cost = cost.getText().toString();
                Log.d(TAG,"part1 done");
                if (TextUtils.isEmpty(s_name)) {
                    Toast.makeText(CreateParty.this, "Name must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(s_address)) {
                    Toast.makeText(CreateParty.this, "Address must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(s_type)) {
                    Toast.makeText(CreateParty.this, "Please specific the type of party!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_email).requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(s_description)) {
                    Toast.makeText(CreateParty.this, "Please describe your party!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_phone).requestFocus();
                    return;
                }
                if (split.isChecked() && TextUtils.isEmpty(s_cost)) {
                    Toast.makeText(CreateParty.this, "Cost must not be empty!", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.signup_cityName).requestFocus();
                    return;
                }
                Log.d(TAG,"part2 done");

                if(split.isChecked()) {
                    information = new Party(s_name, username, s_address, s_type, s_description, s_cost);
                    Log.d(TAG,"part3 done checked");
                }
                else {
                    information = new Party(s_name, username, s_address, s_type, s_description);
                    Log.d(TAG,"part3 done unchecked");
                }
                Geocoder coder = new Geocoder(CreateParty.this);
                List<Address> address;

                try {
                    address = coder.getFromLocationName(s_address, 1);
                    Address location = address.get(0);
                    information.setLat(Double.toString(location.getLatitude()));
                    information.setLng(Double.toString(location.getLongitude()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FirebaseDatabase.getInstance().getReference("Party")
                        .push().setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"part4 done checked");
                        Toast.makeText(CreateParty.this, "Created successfully!", Toast.LENGTH_LONG).show();
                        finish();
                    }});
            }
        });

    }

}
