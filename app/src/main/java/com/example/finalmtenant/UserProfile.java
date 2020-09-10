package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    variables
    TextView name,uName,amount,apartment;
    Button mpesapay,paypalpay;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef,tenantRef;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


//        hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool_bar);

        name=(TextView)findViewById(R.id.full_name);
        uName=(TextView)findViewById(R.id.username);
        amount=(TextView)findViewById(R.id.payment_label);
        apartment=(TextView)findViewById(R.id.apartment_label);
        mpesapay=(Button)findViewById(R.id.pay_mpesa);
        paypalpay=(Button)findViewById(R.id.pay_paypal);

        mpesapay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserProfile.this,MpesaPay.class);
                startActivity(intent);
            }
        });
        paypalpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserProfile.this,PaypalPay.class);
                startActivity(intent);
            }
        });
//        toolbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

//        navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        myRef=FirebaseDatabase.getInstance().getReference().child("Tenants");
        Query specific_user = myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());


       specific_user.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

                   String fullName=(String)snapshot.child("Name").getValue();
                   String userName=(String)snapshot.child("Username").getValue();
                   String rent=(String)snapshot.child("Rent").getValue();
                   String apartmentNo=(String)snapshot.child("Apartment No").getValue();

                   name.setText(fullName);
                   uName.setText(userName);
                   amount.setText(rent);
                   apartment.setText(apartmentNo);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{

            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
