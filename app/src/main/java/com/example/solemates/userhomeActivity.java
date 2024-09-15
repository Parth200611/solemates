package com.example.solemates;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userhomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        getWindow().setNavigationBarColor(ContextCompat.getColor(userhomeActivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(userhomeActivity.this,R.color.nevyblue));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nevyblue)));

        bottomNavigationView = findViewById(R.id.userhomebottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottomnevigationhomeicon);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homemenucategary){

        } else if (item.getItemId() == R.id.homemenumyorder){

        } else if (item.getItemId() == R.id.homemenuHistory) {

        }else if (item.getItemId()  == R.id.homemenumyprofil){
            Intent i = new Intent(userhomeActivity.this, userprofilActivity.class);
            startActivity(i);
        }else if(item.getItemId() == R.id.homemenuaboutus){

        }
        return true;
    }

    homeFragment homeFragment = new homeFragment();
    launchesFragment launchesFragment = new launchesFragment();
    cartFragment cartFragment = new cartFragment();



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.bottomnevigationhomeicon){
            getSupportFragmentManager().beginTransaction().replace(R.id.fluserhomeframe,homeFragment).commit();
        } else if (item.getItemId() == R.id.bottomnevigationlaunches){
            getSupportFragmentManager().beginTransaction().replace(R.id.fluserhomeframe,launchesFragment).commit();
        }else if (item.getItemId() == R.id.bottomnevigationhomecart){
            getSupportFragmentManager().beginTransaction().replace(R.id.fluserhomeframe,cartFragment).commit();
        }

        return true;
    }
}