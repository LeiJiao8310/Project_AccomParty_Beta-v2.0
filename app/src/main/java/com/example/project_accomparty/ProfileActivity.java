package com.example.project_accomparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("Users");
        final TextView username = findViewById(R.id.name_display);
        final TextView loc = findViewById(R.id.loc);
        final TextView phone = findViewById(R.id.phone);
        final TextView emeier = findViewById(R.id.email);
        final String email = getIntent().getStringExtra("uid");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Users user  = postSnapshot.getValue(Users.class);
                    if(user.getEmail().equals(email)) {
                        username.setText("\uD83D\uDC64 "+user.getUsername());
                        loc.setText("\uD83D\uDCCD "+user.getCity());
                        emeier.setText("\uD83D\uDCED "+user.getEmail());
                        phone.setText("☎️ "+user.getPhone());

                    }

                    Log.d("LOG",""+user.getEmail()+"is "+user.getStatus());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
