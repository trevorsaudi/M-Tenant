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

public class SignUp extends AppCompatActivity {

    Button regBtn;
    TextView regToLoginBtn;
    Pattern pattern;
    //variables
    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    //FirebaseDatabase rootNode;
    private DatabaseReference userDetailsReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Users user;


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
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        //initialize an instance of firebase authentication by calling the getinstance() method
        mAuth = FirebaseAuth.getInstance();
        //Initialize an instance of firebase Database by calling the getInstance() method
        database = FirebaseDatabase.getInstance();
        //Initialize an instance of Firebase Database reference by calling the database instance,
        //getting a reference using the getReference() method on the dataabase, and creating
        //a a child node, in out case "Users" where we will store details of registered users
        userDetailsReference = database.getReference().child("Users");

        ///For already registered user we want to redirect them to the login directly without
        //registering them again
//       For this function, setOnlickLIstener on the textView object of redirecting user to Login Activity
//        Create a login activity first using the empty activity template
//        Implement an intent to launch this

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignUp.this,Login.class);
                startActivity(loginIntent);
            }
        });

        //set an onclick listener on your register button, on clicking this button we want to get
//        the username, email, password the user enters ont edit text field
//        further we want to open a new activity called profile activity where will allow our users to
//        set a custom display name and their profile image

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a toast
                Toast.makeText(SignUp.this, "LOADING...",Toast.LENGTH_SHORT).show();
                //get the username entered
                final String email = regEmail.getEditText().getText().toString().trim();
                final String password = regPassword.getEditText().getText().toString().trim();

                final String username = regUsername.getEditText().getText().toString();
                final String phoneNo = regPhoneNo.getEditText().getText().toString();
                final String fullName = regName.getEditText().getText().toString();
                final String as = "admin";

                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password)){
                    //create a new createAccount method that takes ina an email address and password, validates them and
                    //then creates a new user with the [createuserwitheemailandpassword] using the
//                    using the instance of firebase authentication (mauth) we created anc alls addOnCompleteListender
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //override  the oncompelte method where we'll store this registered use ron our database with respect to their unique id's
//
//                            //create a string variable to get uthe user id of currently registered user
//                            String user_id = mAuth.getCurrentUser().getUid();
//                            //create a child node database reference to attach the suer_id to the user node
//                            DatabaseReference current_user_db = userDetailsReference.child(user_id);
//                            //set the username and image on the user' unique path (current_users_db)
//                            current_user_db.child("Username").setValue(username);
//                            current_user_db.child("Image").setValue("Default");
//                            //male a toast to show the user that they've been successfully registred and then
//                            Toast.makeText(SignUp.this,"Registration Successful", Toast.LENGTH_SHORT).show();
//                                    //Create a profile activity using empty acitivty template
//                            //launch the profile acitivty for use to set their preffered profile
//                            Intent profIntent = new Intent(SignUp.this,ProfileActivity.class);
//                            profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(profIntent);

                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
//                                create a child node database reference to attach the use rid to the users node
                                DatabaseReference current_user = userDetailsReference.child(user_id);
                                current_user.child("email").setValue(email);
                                current_user.child("fullName").setValue(fullName);

                                current_user.child("password").setValue(password);
                                current_user.child("phoneNo").setValue(phoneNo);
                                current_user.child("username").setValue(username);
                                current_user.child("as").setValue(as);
                                Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                Intent profIntent = new Intent(SignUp.this, Login.class);
                                profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(profIntent);
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(SignUp.this, "error:" + error, Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }else{
                    Toast.makeText(SignUp.this,"Complete all fields",Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

}//    }