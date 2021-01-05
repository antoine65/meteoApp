package com.example.meteoapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Création d'objets graphique
    ImageView imageViewIcone;
    TextView temperature, ville, date, pays, humidite, coucher, lever, vent;
    ImageView temps;
    String lien;
    LinearLayout meteoApp2;
    private Location gps_loc = null;
    private Location network_loc = null;


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
        coucher = findViewById(R.id.coucher);
        lever = findViewById(R.id.lever);
        vent = findViewById(R.id.vent);

        meteoApp2.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), MeteoApp2.class);//utiliation de la classe intent pour etre rediriger vers l'activité météoApp2
            startActivity(intent);
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            try {
                assert locationManager != null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    return;
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double latitude;
            double longitude;
            Location final_loc;
            if (gps_loc != null) {
                final_loc = gps_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else if (network_loc != null) {
                final_loc = network_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else {
                latitude = 0.0;
                longitude = 0.0;
            }


            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    lien = "https://www.prevision-meteo.ch/services/json/" +addresses.get(0).getLocality();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                        String leVent = current_condition.getString("wnd_spd");
                        temperature.setText(tmp + "°C");
                        vent.setText(leVent + "km/h");
                        date.setText("Date : " + laDate);
                        humidite.setText(lHumidite + " %");
                        Picasso.get().load(icone).into(temps);


                        JSONObject city_info = jsonObject.getJSONObject("city_info");
                        String laVille = city_info.getString("name");
                        String lePays = city_info.getString("country");
                        String leCoucher = city_info.getString("sunset");
                        String leLever = city_info.getString("sunrise");
                        ville.setText("Ville : " + laVille);
                        pays.setText("" + lePays);
                        coucher.setText(leCoucher +" h");
                        lever.setText(leLever +" h");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

}
