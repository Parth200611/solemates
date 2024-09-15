package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.google.android.gms.common.api.Api;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class forgottenpasswordforshopkeeperActivity extends AppCompatActivity {

    EditText etmobileno;
    Button btnnext;
    ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgottenpasswordforshopkeeper);
        getWindow().setNavigationBarColor(ContextCompat.getColor(forgottenpasswordforshopkeeperActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(forgottenpasswordforshopkeeperActivity.this, R.color.nevyblue));
        
        etmobileno = findViewById(R.id.etfrogottenpassshopkeepercheckno);
        btnnext = findViewById(R.id.btnforgottenpassshopkeepernext);
        
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmobileno.getText().toString().isEmpty()){
                    etmobileno.setError("enter mobile no");
                }else if(etmobileno.getText().toString().length()!=10){
                    etmobileno.setError("enter proper mobile no");
                }else{
                    progressDialog = new ProgressDialog(forgottenpasswordforshopkeeperActivity.this);
                    progressDialog.setTitle("verifying");
                    progressDialog.setMessage("Checking your number");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    
                    checknumber();
                }
            }
        });
        
       
    }

    private void checknumber() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("mobileno",etmobileno.getText().toString());

        client.post(urls.shopkeeperchechnumber,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(forgottenpasswordforshopkeeperActivity.this,updateshopkeeperpassworrdActivity.class);
                        i.putExtra("mobileno",etmobileno.getText().toString());
                        startActivity(i);
                    }else{
                        Toast.makeText(forgottenpasswordforshopkeeperActivity.this,"Mobileno is not Proper",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(forgottenpasswordforshopkeeperActivity.this,"server error ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}