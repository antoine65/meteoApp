package com.example.meteoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Création d'objets graphique
    ImageView imageViewIcone;
    TextView textViewTemperature, textViewCondition;
    EditText editText;
    String url;
    TextView meteoApp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meteoApp2 = findViewById(R.id.meteoApp2);
        meteoApp2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeteoApp2.class);//utiliation de la classe intent pour etre rediriger vers l'activité météoApp2
            startActivity(intent);

        });

    }
}