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

public class loginselectActivity extends AppCompatActivity {

    CardView cvcustomer,cvshopkeeper,cvother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginselect);
        getWindow().setNavigationBarColor(ContextCompat.getColor(loginselectActivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(loginselectActivity .this, R.color.nevyblue));
        cvcustomer = findViewById(R.id.cvloginselectioncustomer);
        cvshopkeeper= findViewById(R.id.cvloginselectionshopkeeper);
        cvother = findViewById(R.id.cvSignupother);

        cvcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginselectActivity.this,loginActivity.class);
                startActivity(i);
            }
        });cvshopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginselectActivity.this,shopkeeperloginActivity.class);
                startActivity(i);
            }
        });cvother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginselectActivity.this,adminloginActivity.class);
                startActivity(i);
            }
        });

    }
}