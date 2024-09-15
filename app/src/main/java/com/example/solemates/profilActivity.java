package com.example.solemates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class profilActivity extends AppCompatActivity {

    TextView tvname,tvemailid;
    AppCompatButton btnsignout;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getWindow().setNavigationBarColor(ContextCompat.getColor(profilActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(profilActivity.this, R.color.nevyblue));

        tvname = findViewById(R.id.tvhomename);
        tvemailid = findViewById(R.id.tvhomeemail);
        btnsignout = findViewById(R.id.btnhomesignout);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(profilActivity.this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null){
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();

            tvname.setText(name);
            tvemailid.setText(email);

            btnsignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent =  new Intent(profilActivity.this,loginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });


        }



    }
}