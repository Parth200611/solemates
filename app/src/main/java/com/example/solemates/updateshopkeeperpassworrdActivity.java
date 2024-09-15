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

public class updateshopkeeperpassworrdActivity extends AppCompatActivity {

    EditText etmobileno,etnewpassword,etconfirmmpassword;
    Button btnnext;
    String strmobileno;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateshopkeeperpassworrd);
        getWindow().setNavigationBarColor(ContextCompat.getColor(updateshopkeeperpassworrdActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(updateshopkeeperpassworrdActivity.this, R.color.nevyblue));

        etmobileno=findViewById(R.id.etshopkeeperupdatepasswordmobileno);
        etnewpassword = findViewById(R.id.etshopkeeperupdatepasswordnewpass);
        etconfirmmpassword = findViewById(R.id.etshopkeeperupdatepasswordnewpassconfirm);
        btnnext= findViewById(R.id.btnshopkeeperUPnext);

        strmobileno=getIntent().getStringExtra("mobileno");
        etmobileno.setText(strmobileno);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etnewpassword.getText().toString().isEmpty()){
                    etnewpassword.setError("Enetr new password");
                }else if (etnewpassword.getText().toString().length()<8){
                    etnewpassword.setError("Password be above 8 character");
                } else if (!etnewpassword.getText().toString().matches(".*[A-Z].*")){
                    etnewpassword.setError("Enter Atlest one upper case  character");
                }else if (!etnewpassword.getText().toString().matches(".*[a-z].*")){
                    etnewpassword.setError("Enter Atlest one lower case  character");
                }else if (!etnewpassword.getText().toString().matches(".*[0-9].*")){
                    etnewpassword.setError("Enter Atlest one number  character");
                }else if (!etnewpassword.getText().toString().matches(".*[@,#,$,%,&,*].*")){
                    etnewpassword.setError("Enter Atlest one special  character");
                } else if (!etnewpassword.getText().toString().equals(etconfirmmpassword.getText().toString())) {
                    etconfirmmpassword.setError("password do not match");

                }
                else{
                    progressDialog = new ProgressDialog(updateshopkeeperpassworrdActivity.this);
                    progressDialog.setTitle("Updating Information");
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" +etmobileno.getText().toString(),
                            60, TimeUnit.SECONDS, updateshopkeeperpassworrdActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(updateshopkeeperpassworrdActivity.this,"verification done",Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(updateshopkeeperpassworrdActivity.this,"verification fail",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(otp, forceResendingToken);
                                    Intent intent = new Intent(updateshopkeeperpassworrdActivity.this,otpforforgottenpasswordActivity.class);
                                    intent.putExtra("otp",otp);
                                    intent.putExtra("mobileno",etmobileno.getText().toString());
                                    intent.putExtra("password",etnewpassword.getText().toString());
                                    startActivity(intent);


                                }
                            });

                }
            }
        });



    }
}