package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    //variables
    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn;
    TextView regToLoginBtn;
    Pattern pattern;
    //FirebaseDatabase rootNode;
   private DatabaseReference userDetailsReference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;


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
        regToLoginBtn=findViewById(R.id.login_btn);

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userDetailsReference = database.getReference().child("Users");


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this, "LOADING...",Toast.LENGTH_SHORT).show();
                final String name = regName.getEditText().getText().toString().trim();
                final String email = regEmail.getEditText().getText().toString().trim();
                final String password = regPassword.getEditText().getText().toString().trim();
                final String pNo = regPhoneNo.getEditText().getText().toString().trim();


                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(name)&& !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pNo)) {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = userDetailsReference.child(user_id);
                            current_user_db.child("Username").setValue(name);
                            current_user_db.child("Email").setValue(email);
                            current_user_db.child("Phone Number").setValue(pNo);
                            current_user_db.child("Password").setValue(password);
                            Toast.makeText(SignUp.this,"Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUp.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(SignUp.this,"Complete all fields",Toast.LENGTH_SHORT).show();

                }
            }
        });
////        save data in firebase on button click
//        regBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("user");
//
//
////                get all the values
//                String name = regName.getEditText().getText().toString();
//                String username = regUsername.getEditText().getText().toString();
//                String email = regEmail.getEditText().getText().toString();
//                String phoneNo = regPhoneNo.getEditText().getText().toString();
//                String password = regPassword.getEditText().getText().toString();
//
//                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
//
//                reference.child(phoneNo).setValue(helperClass);
//
//
//            }
//        });


    }

   /* private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            return true;
        }

    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }
//    private Boolean validatePassword() {
//        String val = regName.getEditText().getText().toString();
//        String passwordVal =                //"(?=.*[0-9])" +         //at least 1 digit
//                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(passwordVal);
//        if (val.isEmpty()) {
//            regPassword.setError("Field cannot be empty");
//            return false;
//        } else if (!val.matches(passwordVal)) {
//            regPassword.setError("Password is too weak");
//            return false;
//        } else {
//            regPassword.setError(null);
//            regPassword.setErrorEnabled(false);
//            return true;
//    }


    public void registerUser(View view) {

        if(!validateName()  | !validatePhoneNo() | !validateEmail() | !validateUsername()){
            return;
        }
//        get all the values in the string
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
        reference.child(username).setValue(helperClass);

    }
*/

}
