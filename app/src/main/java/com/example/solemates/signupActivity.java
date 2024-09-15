package com.example.solemates;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class signupActivity extends AppCompatActivity {

    ImageView ivapplogo;
    TextView tvappname, tvtitlesignup;
    EditText etname, etmobileno, etemail, etpassword, etreenterpassword,etusername;

    Button btnsignup;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setNavigationBarColor(ContextCompat.getColor(signupActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(signupActivity.this, R.color.nevyblue));

        ivapplogo = findViewById(R.id.ivsignupapplogo);
        tvappname = findViewById(R.id.tvsignuptitle);
        tvtitlesignup = findViewById(R.id.tvsignuptitle);
        etname = findViewById(R.id.etsignupname);
        etmobileno = findViewById(R.id.etsignupmobileno);
        etemail = findViewById(R.id.etsignupemail);
        etusername = findViewById(R.id.etsignupusername);
        etpassword = findViewById(R.id.etsignuppassword);
        etreenterpassword = findViewById(R.id.etsignupreenterpassword);
        btnsignup = findViewById(R.id.btnsignupsignup);

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
                     progressDialog = new ProgressDialog(signupActivity.this);
                     progressDialog.setTitle("Please Wait");
                     progressDialog.setMessage("Registration is in progress");
                     progressDialog.setCanceledOnTouchOutside(false);
                     progressDialog.show();

                     PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + etmobileno.getText().toString(),
                             60, TimeUnit.SECONDS, signupActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                 @Override
                                 public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                     Toast.makeText(signupActivity.this,"verification successfully Done",Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onVerificationFailed(@NonNull FirebaseException e) {
                                     Toast.makeText(signupActivity.this,"verification failed",Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                     super.onCodeSent(otp, forceResendingToken);
                                     Intent intent = new Intent(signupActivity.this,otp_verificationActivity.class);
                                     intent.putExtra("otp",otp);
                                     intent.putExtra("name",etname.getText().toString());
                                     intent.putExtra("email",etemail.getText().toString());
                                     intent.putExtra("mobileno",etmobileno.getText().toString());
                                     intent.putExtra("username",etusername.getText().toString());
                                     intent.putExtra("userpassword",etpassword.getText().toString());
                                     startActivity(intent);

                                 }
                             });

                    //Userregisterdetailtodatabase();
                }

            }
        });
    }

    private void Userregisterdetailtodatabase() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",etname.getText().toString());
        params.put("email",etemail.getText().toString());
        params.put("mobileno",etmobileno.getText().toString());
        params.put("username",etusername.getText().toString());
        params.put("userpassword",etpassword.getText().toString());

        client.post("http://192.168.1.2:80/solemateAPI/userregisterdetails.php",params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){

                        Intent intent = new Intent(signupActivity.this,loginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(signupActivity.this,"sign up fail",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);


            }
        });





    }
}

