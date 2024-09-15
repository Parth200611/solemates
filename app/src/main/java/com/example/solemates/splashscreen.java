 package com.example.solemates;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splashscreen extends AppCompatActivity {
    ImageView ivlogo;
    TextView tvtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getWindow().setNavigationBarColor(ContextCompat.getColor(splashscreen.this,R.color.nevyblue));
        getWindow().setStatusBarColor(ContextCompat.getColor(splashscreen.this,R.color.nevyblue));

        ivlogo=findViewById(R.id.ivsplashlogo);
        tvtitle=findViewById(R.id.tvsplashtitle);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(splashscreen.this,welcomepageActivity.class);
                startActivity(i);
            }
        },4000);

    }
}