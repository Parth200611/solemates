package com.example.solemates;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.solemates.comman.Networkchangelistner;
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

public class loginActivity extends AppCompatActivity {
    ImageView ivlogo;
    TextView tvappname,tvwelcomemessage,tvmessage,tvforgottenpassword,tvnewuser;
    EditText etusername,etpassword;
    Button btnlogin;
    AppCompatButton btngooglesignin;


    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

Boolean passwordVisible;

Networkchangelistner networkchangelistner = new Networkchangelistner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(loginActivity.this);
        editor = preferences.edit();

        ivlogo=findViewById(R.id.ivloginapplogo);
        tvappname=findViewById(R.id.tvloginappname);
        tvwelcomemessage=findViewById(R.id.tvloginwelcome);
        tvmessage=findViewById(R.id.tvloginmessages);
        tvforgottenpassword=findViewById(R.id.tvloginforgottenpasswoord);
        tvnewuser=findViewById(R.id.tvloginneewuser);
        etusername=findViewById(R.id.etloginusername);
        etpassword=findViewById(R.id.etloginpassword);
        btnlogin=findViewById(R.id.btnloginlogin);
        btngooglesignin = findViewById(R.id.btnloginloginwithgoogle);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(loginActivity.this,googleSignInOptions);
        btngooglesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });





        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("Please enter username");
                }
                else if (etpassword.getText().toString().isEmpty()){
                    etpassword.setError("Please enter password");
                }else{

                    UserLogin();
                }
            }
        });
        tvnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(loginActivity.this,signupActivity.class);
                startActivity(i);
            }
        });

        tvforgottenpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginActivity.this,forgottenpasswordnumberActivity.class);
                startActivity(i);
            }
        });




    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(loginActivity.this, userhomeActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
               Toast.makeText(loginActivity.this,"something went wroong",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter =  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkchangelistner,intentFilter);
    }




    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkchangelistner);
    }

    private void UserLogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("userpassword",etpassword.getText().toString());
        params.put("email",etusername.getText().toString());



        client.post(urls.loginactivity,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        Intent intent = new Intent(loginActivity.this, userhomeActivity.class);
                        editor.putString("username",etusername.getText().toString()).commit();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(loginActivity.this,"log in to fail Try again",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(loginActivity.this,"server error",Toast.LENGTH_SHORT).show();

            }
        });

    }
}