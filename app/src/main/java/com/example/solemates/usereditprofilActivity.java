package com.example.solemates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class usereditprofilActivity extends AppCompatActivity {
    CardView cvemail,cvpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usereditprofil);
        getWindow().setNavigationBarColor(ContextCompat.getColor(usereditprofilActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(usereditprofilActivity.this,R.color.nevyblue));

        cvemail = findViewById(R.id.cvusereditprofileditemail);
        cvpassword = findViewById(R.id.cvusereditprofileditpassword);
        cvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(usereditprofilActivity.this, userusernamecheckforemailupdateActivity.class);
                startActivity(i);
            }
        });
        cvpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(usereditprofilActivity.this,forgottenpasswordnumberActivity.class);
                startActivity(i);
            }
        });


    }
}