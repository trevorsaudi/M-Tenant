package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewTenant extends AppCompatActivity {

    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword,regApartmentNo,Rent;
    Button addTenantBtn;
    private FirebaseDatabase database;
    private DatabaseReference myRef,myRef2;
    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_tenant);


        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regApartmentNo=findViewById(R.id.apartment_no);
        Rent=findViewById(R.id.rent);
        addTenantBtn=findViewById(R.id.addTenant_btn);
        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        insertData();
    }
    private void insertData(){
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference().child("Tenants");
        myRef2=database.getReference().child("login");
        mAuth = FirebaseAuth.getInstance();



        addTenantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = regName.getEditText().getText().toString().trim();
                final String username = regUsername.getEditText().getText().toString().trim();
                final String email = regEmail.getEditText().getText().toString().trim();
                final String pNo = regPhoneNo.getEditText().getText().toString().trim();
                final String password = regPassword.getEditText().getText().toString().trim();
                final String apartmentNo = regApartmentNo.getEditText().getText().toString().trim();
                final String rent = Rent.getEditText().getText().toString().trim();

                /*final Tenants tenants=new Tenants(name,username,email,pNo, apartmentNo,rent,password);
                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(name)&& !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pNo)) {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = myRef.child(user_id);
                            current_user_db.setValue(tenants);

                            Toast.makeText(AddNewTenant.this,"Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddNewTenant.this, TenantsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(AddNewTenant.this,"Complete all fields",Toast.LENGTH_SHORT).show();

                }*/
                Tenants tenants=new Tenants(name,username,email,pNo, apartmentNo,rent,password);
                long mDateTime=9999999999999L-System.currentTimeMillis();
                String mOrderTime=String.valueOf(mDateTime);
                //String user_id = mAuth.getCurrentUser().getUid();
                // DatabaseReference current_user_db = myRef.child(user_id);
                myRef.child(mOrderTime).setValue(tenants).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(AddNewTenant.this,"Adding new tenant successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(AddNewTenant.this,"Adding new tenant successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddNewTenant.this,TenantsActivity.class);
                        startActivity(intent);




                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewTenant.this,"Adding new tenant Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                //Tenants tenants=new Tenants(name,username,email,pNo, apartmentNo,rent,password);
                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(name)&& !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pNo)) {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = myRef.child(user_id);

                            Toast.makeText(AddNewTenant.this,"Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(AddNewTenant.this,TenantsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(AddNewTenant.this,"Complete all fields",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}