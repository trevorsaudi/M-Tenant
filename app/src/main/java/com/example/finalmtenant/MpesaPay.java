package com.example.finalmtenant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MpesaPay extends AppCompatActivity {
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    @BindView(R.id.number) EditText mNumber;
    @BindView(R.id.amount) EditText mAmount;
    @BindView(R.id.button) Button mButton;
    private Daraja daraja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa_pay);



        ButterKnife.bind(this);


        myRef=FirebaseDatabase.getInstance().getReference().child("Payments");
       // Query specific_user = myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference current_user_db = myRef.child(userId);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == mButton){
                    String phonenumber = mNumber.getText().toString().trim(); //collect the number from the user's input
                    String Amount = mAmount.getText().toString().trim(); // amount provided by the user


                    //check validity of a number
                    if(phonenumber.isEmpty() || phonenumber.length() != 10){
                        mNumber.setError("Invalid number");
                        return;
                    }
                    //check validity of a number
                    else if(Integer.valueOf(Amount) <= 0){
                        mAmount.setError("Amount should be more than 0");
                        return;
                    }

                    current_user_db.child("pNo").setValue(phonenumber);
                    current_user_db.child("Amount Paid").setValue(Amount);
                    current_user_db.child("User Id").setValue(userId);
                    current_user_db.child("Paid via").setValue("M-pesa");
                    LNMExpress lnmExpress = new LNMExpress(
                            "174379",
                            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                            TransactionType.CustomerPayBillOnline,
                            Amount,
                            "0704865422",
                            "174379",
                            phonenumber,
                            "http://mpesa-requestbin.herokuapp.com/115r38l1",
                            "test",
                            "test"
                    );
                    daraja.requestMPESAExpress(lnmExpress,
                            new DarajaListener<LNMResult>() {
                                @Override
                                public void onResult(@NonNull LNMResult lnmResult) {
                                    Log.i(MpesaPay.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                                }

                                @Override
                                public void onError(String error) {
                                    Log.i(MpesaPay.this.getClass().getSimpleName(), error);
                                }
                            }
                    );
                }
            }
        });
        daraja = Daraja.with("3ndirZA7MrMXBrDuymH28bpETaLGHWXi", "x11asU8c7S2k3Qs1", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                Log.i(MpesaPay.this.getClass().getSimpleName(), accessToken.getAccess_token());
            }

            @Override
            public void onError(String error) {
                Log.e(MpesaPay.this.getClass().getSimpleName(), error);

            }
        });
    }


}
