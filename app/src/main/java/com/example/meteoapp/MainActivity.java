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
    TextView temperature, ville, date, pays, humidite;
    ImageView temps;
    String lien;
    TextView meteoApp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature = findViewById(R.id.temperature);
        date = findViewById(R.id.date);
        ville = findViewById(R.id.ville);
        pays = findViewById(R.id.pays);
        temps = findViewById(R.id.temps);
        humidite = findViewById(R.id.humidite);
        meteoApp2 = findViewById(R.id.meteoApp2);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            lien = "https://www.prevision-meteo.ch/services/json/" + extra.getString("villeUse");

        }
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, lien,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        // current_condition
                        JSONObject current_condition = jsonObject.getJSONObject("current_condition");
                        String icone = current_condition.getString("icon_big");
                        String tmp = current_condition.getString("tmp");
                        String laDate = current_condition.getString("date");
                        String condition = current_condition.getString("condition");
                        String lHumidite = current_condition.getString("humidity");
                        temperature.setText(tmp + "°C");
                        date.setText("Date : " + laDate);
                        humidite.setText(lHumidite+" %");
                        Picasso.get().load(icone).into(temps);


                        JSONObject city_info = jsonObject.getJSONObject("city_info");
                        String laVille = city_info.getString("name");
                        String lePays = city_info.getString("country");
                        ville.setText("Ville : " + laVille);
                        pays.setText(""+ lePays);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);


        meteoApp2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MeteoApp2.class);//utiliation de la classe intent pour etre rediriger vers l'activité météoApp2
            startActivity(intent);

        });

    }
}