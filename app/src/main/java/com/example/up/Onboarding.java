package com.example.up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        getSupportActionBar().hide();
    }

    public void onClickLogin(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void onClickRegister(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}