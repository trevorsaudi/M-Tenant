package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSession extends AppCompatActivity {

    Button login;
    Switch active;
    private TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_session);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        active = findViewById(R.id.active);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String input1 = username.getEditText().toString().trim();
                        String input2 = password.getEditText().toString().trim();

                        if (snapshot.child(input1).exists()) {

                            if (snapshot.child(input1).child("password").getValue(String.class).equals(input2)) {

                                if (active.isChecked()) {
                                    if (snapshot.child(input1).child("as").getValue(String.class).equals("admin")) {

                                        preferences.setDataLogin(LoginSession.this, true);
                                        preferences.setDataAs(LoginSession.this, "admin");
                                        startActivity(new Intent(LoginSession.this, Landlord_profile.class));

                                    } else if (snapshot.child(input1).child("as").getValue(String.class).equals("user")) {
                                        preferences.setDataLogin(LoginSession.this, true);
                                        preferences.setDataAs(LoginSession.this, "user");
                                        startActivity(new Intent(LoginSession.this, UserProfile.class));

                                    }
                                } else {
                                    if (snapshot.child(input1).child("as").getValue(String.class).equals("admin")) {

                                        preferences.setDataLogin(LoginSession.this, false);
                                        startActivity(new Intent(LoginSession.this, Landlord_profile.class));
                                    } else if (snapshot.child(input1).child("as").equals("user")){
                                        preferences.setDataLogin(LoginSession.this, false);
                                        startActivity(new Intent(LoginSession.this, Landlord_profile.class));

                                    }
                                }


                            } else {

                                Toast.makeText(LoginSession.this, "Incorrect password", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(LoginSession.this, "Incorrect username", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(LoginSession.this, Landlord_profile.class));

                finish();


            } else if(preferences.getDataAs(this).equals("user")){
                startActivity(new Intent(LoginSession.this, UserProfile.class));
                finish();

            }
        }
    }
}
