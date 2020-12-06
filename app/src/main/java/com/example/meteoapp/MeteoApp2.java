package com.example.meteoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MeteoApp2 extends AppCompatActivity {

    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo_app2);
        editText = findViewById(R.id.editText);


        findViewById(R.id.button).setOnClickListener(v -> {
           String ville= editText.getText().toString();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("villeUse",ville);
            startActivity(intent);
        });

    }
}