package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class shopkeeperloginActivity extends AppCompatActivity {

    EditText etusername,etpassword;
    CheckBox cbshowpassword;
    TextView tvforgottenpassword,tvnewuser;

    AppCompatButton acbgooglelogin;
    Button btnlogin;

    ProgressDialog progressDialog;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperlogin);
        getWindow().setNavigationBarColor(ContextCompat.getColor(shopkeeperloginActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(shopkeeperloginActivity.this, R.color.lightblu));
        etusername = findViewById(R.id.etloginshopkeeperusername);
        etpassword = findViewById(R.id.etloginshopkeeperpassword);
        cbshowpassword = findViewById(R.id.cbloginshopkeepershowpass);
        tvforgottenpassword= findViewById(R.id.tvloginshopkeeperforgottenpasswoord);
        tvnewuser = findViewById(R.id.tvloginshopkeeperneewuser);
        btnlogin = findViewById(R.id.btnloginshopkeepperlogin);
        acbgooglelogin = findViewById(R.id.btnloginshopkeeperloginwithgoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(shopkeeperloginActivity.this,googleSignInOptions);
        acbgooglelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("Enter the username");
                }else if(etpassword.getText().toString().isEmpty()){
                    etpassword.setError("enter the password");
                }else{
                    progressDialog=new ProgressDialog(shopkeeperloginActivity.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please wait You are been login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    shopkeeperlogin();
                }
            }
        });

        cbshowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });
        tvforgottenpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shopkeeperloginActivity.this,forgottenpasswordforshopkeeperActivity.class);
                startActivity(intent);
            }
        });
        tvnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shopkeeperloginActivity.this,shopkeepersignupActivity.class);
                startActivity(intent);
            }
        });






    }

    private void shopkeeperlogin() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("shopkeeperpassword",etpassword.getText().toString());

        client.post(urls.shopkeeperlogin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent  i = new Intent(shopkeeperloginActivity.this,shopkeeperhomeactivityActivity.class);
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(shopkeeperloginActivity.this,"Data not present sign up",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(shopkeeperloginActivity.this,"Server error",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void signin() {
        Intent intent= googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(shopkeeperloginActivity.this,userhomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(shopkeeperloginActivity.this,"Error in server",Toast.LENGTH_SHORT).show();
            }
        }

    }
}