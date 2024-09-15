package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class adminloginActivity extends AppCompatActivity {

    EditText etusername,etpassword;
    Button btnlogin;
    TextView tvdelivery;
    CheckBox cbshowpassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminlogin);
        getWindow().setNavigationBarColor(ContextCompat.getColor(adminloginActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(adminloginActivity.this, R.color.nevyblue));

        etusername = findViewById(R.id.etloginadminusername);
        etpassword = findViewById(R.id.etloginadminpassword);
        btnlogin = findViewById(R.id.btnloginadminlogin);
        cbshowpassword = findViewById(R.id.cbadminloginshowpassword);
        tvdelivery = findViewById(R.id.tvloginadmindelivery);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("please enter the username");
                }else if(etpassword.getText().toString().isEmpty()){
                    etpassword.setError("please enter the password");
                }else{
                    progressDialog = new ProgressDialog(adminloginActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Log in in process");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    adinlogin();


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


        tvdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminloginActivity.this, deliveryloginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void adinlogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("adminpassword",etpassword.getText().toString());

        client.post(urls.adminlogin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(adminloginActivity.this,admminhomeActivity.class);
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(adminloginActivity.this,"data not Present connect to administrator",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(adminloginActivity.this,"data not Present connect to administrator",Toast.LENGTH_SHORT).show();
            }
        });






    }
}