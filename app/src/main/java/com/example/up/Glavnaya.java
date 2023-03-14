package com.example.up;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Glavnaya extends AppCompatActivity {

    private TextView text;
    private ImageView img;

    private AdapterVertikal adapterVertikal;
    private List<Maska_Vertikal> maska_vertikals = new ArrayList<>();

    private AdapterHoriz adapterHoriz;
    private List<MaskaHoriz> maskaHorizs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavnaya);

        getSupportActionBar().hide();

        text = findViewById(R.id.Glavnaya_Name);
        text.setText("С возвращением " + Login.maskaUser.getNickName() + "!");
        img = findViewById(R.id.Glavnaya_Image);
        new DownloadImageTask((ImageView) img).execute(Login.maskaUser.getAvatar());

        ListView listView = findViewById(R.id.Profile_ListFoto);
        adapterVertikal = new AdapterVertikal(Glavnaya.this, maska_vertikals);
        listView.setAdapter(adapterVertikal);

        RecyclerView recyclerView = findViewById(R.id.List_Horizontal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapterHoriz = new AdapterHoriz(Glavnaya.this, maskaHorizs);
        recyclerView.setAdapter(adapterHoriz);

        new Get_quotes().execute();
        new Get_feelings().execute();
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_quotes extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //вызываем нашу API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /*
                BufferedReader успрощает чтение текста из потока символов
                InputStreamReader преводит поток байтов в поток символов
                connection.getInputStream() получает поток байтов
                */
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);//кладет строковое значение в потоке
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
                JSONArray tempArray= object.getJSONArray("data") ;
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject Json = tempArray.getJSONObject(i);
                    Maska_Vertikal temp = new Maska_Vertikal(
                            Json.getInt("id"),
                            Json.getString("title"),
                            Json.getString("image"),
                            Json.getString("description")
                    );

                    maska_vertikals.add(temp);
                    adapterVertikal.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
                Toast.makeText(Glavnaya.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_feelings extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");//Строка подключения к нашей API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //вызываем нашу API

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /*
                BufferedReader успрощает чтение текста из потока символов
                InputStreamReader преводит поток байтов в поток символов
                connection.getInputStream() получает поток байтов
                */
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);//кладет строковое значение в потоке
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                maskaHorizs.clear();
                adapterHoriz.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskaHoriz tempProduct = new MaskaHoriz(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getInt("position"),
                            productJson.getString("image")
                    );
                    maskaHorizs.add(tempProduct);
                    adapterHoriz.notifyDataSetChanged();
                }
                maskaHorizs.sort(Comparator.comparing(MaskaHoriz::getPosition));
                adapterHoriz.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Glavnaya.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickListnet(View v) {
        Intent intent = new Intent(this, Listnet.class);
        startActivity(intent);
    }

    public void onClickProfile(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}