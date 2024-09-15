package com.example.solemates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcomepageActivity extends AppCompatActivity {
    TextView tvwelcometext,tvwelcomemessage;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        getWindow().setNavigationBarColor(ContextCompat.getColor(welcomepageActivity.this,R.color.nevyblue));
        getWindow().setStatusBarColor(ContextCompat.getColor(welcomepageActivity.this,R.color.nevyblue));

        tvwelcometext=findViewById(R.id.tvwelcomewelcometext);
        tvwelcomemessage=findViewById(R.id.tvwelcomewelcomemessage);
        btnlogin=findViewById(R.id.btnwlcomelogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(welcomepageActivity.this,loginselectActivity.class);
                startActivity(i);
            }
        });

    }
}