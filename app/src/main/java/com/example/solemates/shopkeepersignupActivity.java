package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class shopkeepersignupActivity extends AppCompatActivity {
    EditText etname, etmobileno, etemail, etpassword, etreenterpassword,etusername;

    Button btnsignup;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepersignup);
        getWindow().setNavigationBarColor(ContextCompat.getColor(shopkeepersignupActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(shopkeepersignupActivity.this, R.color.nevyblue));
        etname=findViewById(R.id.etsignupshopkeepername);
        etmobileno=findViewById(R.id.etsignupshopkeepermobileno);
        etemail=findViewById(R.id.etsignupshopkeeperemail);
        etusername = findViewById(R.id.etsignupshopkeeperusername);
        etpassword = findViewById(R.id.etsignupshopkeeperpassword);
        etreenterpassword=findViewById(R.id.etsignupshopkeeperreenterpassword);
        btnsignup = findViewById(R.id.btnsignupshopkeepersignup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etname.getText().toString().isEmpty()) {
                    etname.setError("Please enter Username");
                } else if (etmobileno.getText().toString().isEmpty()) {
                    etmobileno.setError("Enetr the Mobile Number");
                } else if (etmobileno.getText().toString().length() != 10) {
                    etmobileno.setError("Enter  the proper Mobile Number");
                } else if (!etemail.getText().toString().contains("@") && !etemail.getText().toString().contains(".com")) {
                    etemail.setError("enter Proper Emaiil Id");
                } else if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("Please enter username");
                } else if (etusername.getText().toString().length() < 8) {
                    etusername.setError("username must be atlest 8  character");
                } else if (!etusername.getText().toString().matches(".*[A-Z].*")) {
                    etusername.setError("Please enter atlest One upper case letter");
                } else if (!etusername.getText().toString().matches(".*[a-z].*")) {
                    etusername.setError("Please enter atlest One lower case letter");
                } else if (!etusername.getText().toString().matches(".*[0-9].*")) {
                    etusername.setError("Please enter atlest One number");
                } else if (!etusername.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etusername.setError("Please enter atlest One special symbol");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("Please enter username");
                } else if (etpassword.getText().toString().length() < 8) {
                    etpassword.setError("username must be atlest 8  character");
                } else if (!etpassword.getText().toString().matches(".*[A-Z].*")) {
                    etpassword.setError("Please enter atlest One upper case letter");
                } else if (!etpassword.getText().toString().matches(".*[a-z].*")) {
                    etpassword.setError("Please enter atlest One lower case letter");
                } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                    etpassword.setError("Please enter atlest One number");
                } else if (!etpassword.getText().toString().matches(".*[!,@,#,$,%,&,*].*")) {
                    etpassword.setError("Please enter atlest One special symbol");
                } else if (!etpassword.getText().toString().equals(etreenterpassword.getText().toString())) {
                    etreenterpassword.setError("password do not match");

                } else {
                    progressDialog =  new ProgressDialog(shopkeepersignupActivity.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("verifying please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + etmobileno.getText().toString(), 60, TimeUnit.SECONDS, shopkeepersignupActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            Toast.makeText(shopkeepersignupActivity.this, "Verification Done", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(shopkeepersignupActivity.this, "Verification Fail", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(otp, forceResendingToken);
                            Intent intent = new Intent(shopkeepersignupActivity.this,verificationforshopkeeperActivity.class);
                            intent.putExtra("otp",otp);
                            intent.putExtra("name",etname.getText().toString());
                            intent.putExtra("mobileno",etmobileno.getText().toString());
                            intent.putExtra("emailid",etemail.getText().toString());
                            intent.putExtra("username",etusername.getText().toString());
                            intent.putExtra("password",etpassword.getText().toString());
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }
}