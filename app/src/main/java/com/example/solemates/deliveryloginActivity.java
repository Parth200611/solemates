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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class deliveryloginActivity extends AppCompatActivity {
    EditText etusername,etpassword;
    CheckBox  cbshowpassword;
    Button btnlogin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deliverylogin);
        getWindow().setNavigationBarColor(ContextCompat.getColor(deliveryloginActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(deliveryloginActivity.this, R.color.nevyblue));

        etusername = findViewById(R.id.etlogindeliveryusername);
        etpassword = findViewById(R.id.etlogindeliverypassword);
        cbshowpassword = findViewById(R.id.cblogindeliveryshowpassword);
        btnlogin = findViewById(R.id.btnlogindeliverylogin);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()){
                    etusername.setError("please enter the username");
                } else if (etpassword.getText().toString().isEmpty()){
                    etpassword.setError("plese enter the password");
                }else{
                    progressDialog = new ProgressDialog(deliveryloginActivity.this);
                    progressDialog.setTitle("Log In");
                    progressDialog.setMessage("please wait process in progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    deliverylogin();
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


    }

    private void deliverylogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",etusername.getText().toString());
        params.put("deliverypassword",etpassword.getText().toString());

        client.post(urls.delliverylogin,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")){
                        progressDialog.dismiss();
                        Intent i = new Intent(deliveryloginActivity.this,deliverylhomeActivity.class);
                        startActivity(i);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(deliveryloginActivity.this,"Error during login",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(deliveryloginActivity.this,"server Error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}