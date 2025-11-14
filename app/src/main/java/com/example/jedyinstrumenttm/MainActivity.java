package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jedyinstrumenttm.data.DB;

public class MainActivity extends AppCompatActivity {

    private int loading_time = 4000; //4 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DB.syncData(MainActivity.this);
                DB.generateDummyData(MainActivity.this);
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);
                finish();
            }
        }, loading_time);
    }
}