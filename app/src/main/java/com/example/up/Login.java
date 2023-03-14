package com.example.up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    Users use = new Users( "","");
    final static String userVariableKey = "USER_VARIABLE";

    public static MaskaUser maskaUser;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        email = findViewById(R.id.EditText_Email);
        password = findViewById(R.id.EditText_Password);
        getData();

        /*email = findViewById(R.id.EditText_Email);
        button = findViewById(R.id.Button_SignIn);*/

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public void onClickAvtoris(View v) {
        if(email.getText().toString().equals("") || password.getText().toString().equals(""))
        {
            Toast.makeText(Login.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(email.getText().toString());
            boolean b = m.find();
            if(b)
            {
                Avtoriz();
            }
            else
            {
                Toast.makeText(Login.this, "Поле Email обязательно должен содержать символ '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Avtoriz()
    {
        String email_str = String.valueOf(email.getText());
        String password_str = String.valueOf(password.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        UserModel modelSendUser = new UserModel(email_str, password_str);
        Call<MaskaUser> call = retrofitAPI.createUser(modelSendUser);
        call.enqueue(new Callback<MaskaUser>() {
            @Override
            public void onResponse(Call<MaskaUser> call, Response<MaskaUser> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "Пользователь с такой почтой и паролем не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        saveData();
                        maskaUser = response.body();
                        Intent intent = new Intent(Login.this, Glavnaya.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<MaskaUser> call, Throwable t) {
                Toast.makeText(Login.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(userVariableKey, use);
        saveData();
        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // получаем объект User в переменную
        use=(Users)savedInstanceState.getSerializable(userVariableKey);
        EditText etLogin=findViewById(R.id.EditText_Email);
        EditText etPassword=findViewById(R.id.EditText_Password);
        etLogin.setText(use.getLogin());
        etPassword.setText(use.getPassword());
    }

    public  void saveData()
    {
        // получаем введенные данные
        EditText etLogin=findViewById(R.id.EditText_Email);
        EditText etPassword=findViewById(R.id.EditText_Password);

        String login=etLogin.getText().toString();
        String password=etPassword.getText().toString();
        use=new Users(login,password);
    }

    public void getData()
    {
        // получаем сохраненные данные
        EditText etLogin=findViewById(R.id.EditText_Email);
        EditText etPassword=findViewById(R.id.EditText_Password);
        etLogin.setText(use.getLogin());
        etPassword.setText(use.getPassword());

    }

    /*private boolean EmailAddress(EditText email) {
        String emailInput = email.getText().toString();
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Intent intent = new Intent(Login.this, Glavnaya.class);
            startActivity(intent);
            Toast.makeText(this, "Все хорошо", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Введен неправильный Email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }*/

    public void onClickRegister(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /*public void onClickAvtoris(View v) {
        Intent intent = new Intent(this, Glavnaya.class);
        startActivity(intent);
    }*/
}
