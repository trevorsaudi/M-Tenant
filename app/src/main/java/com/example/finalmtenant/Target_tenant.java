package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Target_tenant extends AppCompatActivity {
    EditText textViewName,textViewUsername,textViewApartmentno,textViewRent,textViewEmail,textViewPassword,textViewpNo;
    DatabaseReference reference;
    Button updateBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_tenant);
        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewName = findViewById(R.id.textViewName);
        textViewUsername = findViewById(R.id.textViewUserName);
        textViewApartmentno = findViewById(R.id.textViewApartmentNo);
        textViewRent = findViewById(R.id.textViewRent);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPassword = findViewById(R.id.textViewPassword);
        textViewpNo= findViewById(R.id.textViewPhoneNumber);
        updateBtn=findViewById(R.id.update);

        reference = FirebaseDatabase.getInstance().getReference("Tenants");
        Log.w("message", reference.toString());

        String key = getIntent().getStringExtra("key");
        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object object = snapshot.child("email").getValue();
                String name = snapshot.child("name").getValue().toString();
                String pNo = snapshot.child("pNo").getValue().toString();
                String rent = snapshot.child("rent").getValue().toString();
                String username = snapshot.child("username").getValue().toString();
                String apartmentNo = snapshot.child("apartmentNo").getValue().toString();
                String password = snapshot.child("password").getValue().toString();

                textViewEmail.setText(""+object);
                textViewName.setText(name);
                textViewUsername.setText(username);
                textViewApartmentno.setText(apartmentNo);
                textViewRent.setText(rent);
                textViewPassword.setText(password);
                textViewpNo.setText(pNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name =textViewName.getText().toString().trim();
                final String username = textViewUsername.getText().toString().trim();
                final String email = textViewEmail.getText().toString().trim();
                final String pNo = textViewpNo.getText().toString().trim();
                final String password = textViewPassword.getText().toString().trim();
                final String apartmentNo = textViewApartmentno.getText().toString().trim();
                final String rent = textViewRent.getText().toString().trim();

                updateTenant(name,username,email,pNo,password,apartmentNo,rent);

            }
        });

    }

    private void updateTenant(final String name, final String username, final String email, final String pNo, final String password,final String apartmentNo, final String rent) {
        Query query = reference.orderByChild("password").equalTo(password);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ds.getRef().child("name").setValue(name);
                    ds.getRef().child("username").setValue(username);
                    ds.getRef().child("email").setValue(email);
                    ds.getRef().child("pNo").setValue(pNo);
                    ds.getRef().child("password").setValue(password);
                    ds.getRef().child("apartmentNo").setValue(apartmentNo);
                    ds.getRef().child("rent").setValue(rent);
                }
                Toast.makeText(Target_tenant.this,"Succesfully updated",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Target_tenant.this,TenantsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Target_tenant.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
