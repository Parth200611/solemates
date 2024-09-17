package com.example.solemates;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.solemates.comman.urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class userprofilActivity extends AppCompatActivity {

    ImageView ivprofilimage;
    Button btneditprofil;
    TextView tvname,tvemailid,tvmobileno,tvusername,tvedit;
    AppCompatButton btnlogout,btnedit;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    SharedPreferences preferences;
    String strusername;
    ProgressDialog progressDialog;
    CircleImageView civprofilimage;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userprofil);
        getWindow().setStatusBarColor(ContextCompat.getColor(userprofilActivity.this,R.color.nevyblue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(userprofilActivity.this,R.color.white));
        
     
       civprofilimage=findViewById(R.id.civuserprofilprofilimage);
        btneditprofil = findViewById(R.id.btnuserprofileditimage);
        tvname = findViewById(R.id.tvuserprofilname);
        tvemailid = findViewById(R.id.tvuserprofilemailid);
       tvmobileno = findViewById(R.id.tvuserprofilmobileno);
       tvusername = findViewById(R.id.tvuserprofilusername);
        btnlogout = findViewById(R.id.acbtnuserprofillogout);
      btnedit = findViewById(R.id.acbtnuserprofiledit);


        preferences = PreferenceManager.getDefaultSharedPreferences(userprofilActivity.this);
        strusername = preferences.getString("username","");


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userprofilActivity.this,usereditprofilActivity.class);
                startActivity(i);
            }
        });
        
        
        
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(userprofilActivity.this,
                googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null){
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();


            tvname.setText(name);
            tvemailid.setText(email);


            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent =  new Intent(userprofilActivity.this,loginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });






        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(userprofilActivity.this);
        progressDialog.setTitle("My Profil");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        
        getmyDetails();
    }

    private void getmyDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strusername);
        params.put("email",strusername);

        client.post(urls.getmydetails,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    progressDialog.dismiss();
                    JSONArray jsonArray = response.getJSONArray("getuserdetails");
                    for (int i= 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String strimage = jsonObject.getString("images");
                        String emailid = jsonObject.getString("email");
                        String mobileno = jsonObject.getString("mobileno");
                        String username = jsonObject.getString("username");

                        tvname.setText(name);
                        tvemailid.setText(emailid);
                       tvmobileno.setText(mobileno);
                       tvusername.setText(username);

                        Glide.with(userprofilActivity.this)
                                .load(urls.address+"images/"+strimage)
                                .skipMemoryCache(true)
                                .error(R.drawable.noimage)
                                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                                .override(800, 800) // Resize the image to 800x800 pixels
                                .into(civprofilimage);

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(userprofilActivity.this,"server Error try again later",Toast.LENGTH_SHORT).show();
            }
        });
    }
}