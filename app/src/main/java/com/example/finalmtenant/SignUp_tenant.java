package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp_tenant extends AppCompatActivity {

    Button regBtn;
    TextView regToLoginBtn;
    Pattern pattern;
    //variables
    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    //FirebaseDatabase rootNode;
    private DatabaseReference userDetailsReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private User_tenant user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //hooks to all xml elements in acitivty_sign_up.xml
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.login_btn);


        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp_tenant.this,  Login.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userDetailsReference = database.getReference().child("login");


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = regEmail.getEditText().getText().toString().trim();
                final String password = regPassword.getEditText().toString();

                if (!TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Enter email and password",
//                            Toast.LENGTH_SHORT).show();
//                    return;
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            String user_id = mAuth.getCurrentUser().getUid();
                            final String email = regEmail.getEditText().getText().toString().trim();
                            final String password = regPassword.getEditText().getText().toString().trim();

                            final String username = regUsername.getEditText().getText().toString();
                            final String phoneNo = regPhoneNo.getEditText().getText().toString();
                            final String fullName = regName.getEditText().getText().toString();
                            final String as = "user";

//                user  = new User_tenant( email,  password,  fullName,  phoneNo,  username );
                            DatabaseReference current_user = userDetailsReference.child(user_id);
                            current_user.child("email").setValue(email);
                            current_user.child("fullName").setValue(fullName);

                            current_user.child("password").setValue(password);
                            current_user.child("phoneNo").setValue(phoneNo);
                            current_user.child("username").setValue(username);
                            current_user.child("as").setValue(as);
                            Toast.makeText(SignUp_tenant.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            Intent profIntent = new Intent(SignUp_tenant.this, Login.class);
                            profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(profIntent);

                        }
                    });
                }


            }

        });
    }
}


//
//    private void registerUser(final String email, final String password) {
//
//        mAuth.createUserWithEmailAndPassword(email, password).
//                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            String userID = mAuth.getCurrentUser().getUid();
//                            DatabaseReference current_user = userDetailsReference.child(userID);
//                            current_user.child("email").setValue(email);
//                            current_user.child("fullName").setValue(regName);
//
//                            current_user.child("password").setValue(password);
//                            current_user.child("phoneNo").setValue(regPhoneNo);
//                            current_user.child("username").setValue(regUsername);
//
//
//
////                                    Sign in success, update UI with signed in user's information
//                            Log.d("SignUp", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
////                                    if sign in fails, display a message to the user
//                            Log.w("SignUp", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(SignUp.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    }
//
//                });

//    }

//    public void updateUI(FirebaseUser currentUser){
//        String keyId = userDetailsReference.getKey();
//        userDetailsReference.child(keyId).setValue(user);
//        Intent loginIntent = new Intent(this, Login.class);
//        startActivity(loginIntent);
//
//    }