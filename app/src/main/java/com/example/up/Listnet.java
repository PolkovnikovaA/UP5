package com.example.up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Listnet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listnet);

        getSupportActionBar().hide();
    }
}