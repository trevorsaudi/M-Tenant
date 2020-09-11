package com.example.finalmtenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button  login_btn;
    ImageView image;
    TextView callSignUp,logoText, sloganText;
    private TextInputLayout mail, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        mail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);



        callSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image,"logo_image");
                pairs[1] = new Pair<View, String>(logoText,"logo_text" );
                pairs[2] = new Pair<View, String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View, String>(mail,"username_tran");
                pairs[4] = new Pair<View, String>(password,"password_tran");
                pairs[5] = new Pair<View, String>(login_btn,"button_tran");
                pairs[6] = new Pair<View, String>(callSignUp,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent, options.toBundle());
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "PROCESSING...", Toast.LENGTH_SHORT).show();
                String email = mail.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();

                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pass)){
//                    use firebase authentication instance you create and call the method
//                    signInwithemailandpassword method passing the email and password you got from the views
//                    Further call the addoncompletelistener() method to handle the authentication result
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new
                                                                                                   OnCompleteListener<AuthResult>() {
                                                                                                       @Override
                                                                                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                           if(task.isSuccessful()){
//                                       create a method that will check if a user exists in the database reference
                                                                                                               checkUserExistence();
                                                                                                           }else{
                                                                                                               Toast.makeText(Login.this,"Couldn't login, user not found", Toast.LENGTH_SHORT).show();

                                                                                                           }
                                                                                                       }


                                                                                                   });
                }else{
                    Toast.makeText(Login.this, "Complete all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //    check if the user exists
    public void checkUserExistence(){
//        check the user existence of the user using the user id in users database referenve
        final String user_id = mAuth.getCurrentUser().getUid();
//        call the method addvalueeventlistener on the database referenve of the user to determine if the current userID supplied exists in our database reference
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                get a snapshot of the users database reference to determine if current user exists
                if(datasnapshot.hasChild(user_id)){
//                    if the user exists direct the user to teh mainactivity
                    Intent mainPage = new Intent(Login.this, Landlord_profile.class);
                    startActivity(mainPage);

                }else{
//                    if the user id does not exist show a toast
                    Toast.makeText(Login.this,"User not registered",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}