package com.example.melik.geocharge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Melik on 08/11/2015.
 */
public class SettingsActivity extends AppCompatActivity {

    //Classe pour les Réglages

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Réglages");
        setContentView(R.layout.activity_settings);
    }
}
