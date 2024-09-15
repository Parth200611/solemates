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

import com.example.solemates.comman.urls;
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

public class forgottenpasswordnumberActivity extends AppCompatActivity {

    EditText etmobileno,etpassword;
    Button btnnext;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgottenpasswordnumber);
        getWindow().setNavigationBarColor(ContextCompat.getColor(forgottenpasswordnumberActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(forgottenpasswordnumberActivity.this, R.color.nevyblue));

        etmobileno = findViewById(R.id.etfpmobileno);
        btnnext = findViewById(R.id.btnfpnext);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmobileno.getText().toString().isEmpty()){
                    etmobileno.setError("enter the proper number");
                }else if (etmobileno.getText().toString().length() != 10){
                    etmobileno.setError("Enter the proper number");
                }
                else {
                    progressDialog = new ProgressDialog(forgottenpasswordnumberActivity.this);
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("checking for your data");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    checknumberindatabasetoverify();



                }
            }
        });



    }

    private void checknumberindatabasetoverify() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("mobileno",etmobileno.getText().toString());

        client.post(urls.forgottenpasswordnumbercheckactivity,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(forgottenpasswordnumberActivity.this,updatepasswordActivity.class);
                        i.putExtra("umobileno",etmobileno.getText().toString());
                        startActivity(i);
                    }else
                    {
                        Toast.makeText(forgottenpasswordnumberActivity.this, "Mobile no not present", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(forgottenpasswordnumberActivity.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}