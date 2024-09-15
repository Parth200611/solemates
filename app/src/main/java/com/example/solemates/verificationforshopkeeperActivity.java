package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class verificationforshopkeeperActivity extends AppCompatActivity {
    TextView tvmobileno,tvresendotp;
    EditText etinput1,etinput2,etinput3,etinput4,etinput5,etinput6;
    Button btnverify;

    String strotp,strname,strmobileno,stremailid,strusername,strpassword;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationforshopkeeper);
        getWindow().setNavigationBarColor(ContextCompat.getColor(verificationforshopkeeperActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(verificationforshopkeeperActivity.this, R.color.nevyblue));
        tvmobileno = findViewById(R.id.tvotpforshopkeepermobileno);
        tvresendotp = findViewById(R.id.tvotpshopkeeperresendotp);
        etinput1= findViewById(R.id.etotpshopkeeperinput1);
        etinput2= findViewById(R.id.etotpshopkeeperinput2);
        etinput3= findViewById(R.id.etotpshopkeeperinput3);
        etinput4= findViewById(R.id.etotpshopkeeperinput4);
        etinput5= findViewById(R.id.etotpshopkeeperinput5);
        etinput6= findViewById(R.id.etotpshopkeeperinput6);
        btnverify=findViewById(R.id.btnotpshopkeeperverify);


        strotp = getIntent().getStringExtra("otp");
        strname=getIntent().getStringExtra("name");
        strmobileno = getIntent().getStringExtra("mobileno");
        stremailid = getIntent().getStringExtra("emailid");
        strusername=getIntent().getStringExtra("username");
        strpassword = getIntent().getStringExtra("password");

        tvmobileno.setText(strmobileno);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etinput1.getText().toString().trim().isEmpty() || etinput2.getText().toString().trim().isEmpty() ||
                etinput3.getText().toString().trim().isEmpty() || etinput4.getText().toString().trim().isEmpty() || etinput5.getText().toString().trim().isEmpty()
                || etinput6.getText().toString().trim().isEmpty()){
                    Toast.makeText(verificationforshopkeeperActivity.this, "Enter the otp properly", Toast.LENGTH_SHORT).show();
                }
                String otpcode=etinput1.getText().toString()+etinput2.getText().toString()+etinput3.getText().toString()+etinput4.getText().toString()+etinput5.getText().toString()+etinput6.getText().toString();
                if(strotp != null){
                progressDialog = new ProgressDialog(verificationforshopkeeperActivity.this);
                progressDialog.setTitle("verifying");
                progressDialog.setMessage("please wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(strotp,otpcode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                shopkeepersignup();
                            }else{

                                progressDialog.dismiss();
                                Toast.makeText(verificationforshopkeeperActivity.this,"verification fail",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
        tvresendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+strmobileno, 60, TimeUnit.SECONDS,verificationforshopkeeperActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(otp, forceResendingToken);
                       strotp=otp;
                    }
                });
            }
        });


        Inputotp();

    }

    private void shopkeepersignup() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",strname);
        params.put("mobileno",strmobileno);
        params.put("emailid",stremailid);
        params.put("username",strusername);
        params.put("shopkeeperpassword",strpassword);

        client.post(urls.shopkeeperverification,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String ststus = response.getString("success");
                    if(ststus.equals("1")) {
                        Intent i = new Intent(verificationforshopkeeperActivity.this, shopkeeperloginActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(verificationforshopkeeperActivity.this,"data already present",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(verificationforshopkeeperActivity.this,"server Error",Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void Inputotp() {
        etinput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty()){
                etinput2.requestFocus();
            }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etinput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty()){
                etinput3.requestFocus();
            }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etinput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etinput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    etinput6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}