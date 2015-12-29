package com.example.melik.geocharge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by Melik on 08/11/2015.
 */
public class SettingsActivity extends AppCompatActivity {

    //Classe pour les Réglages

    private Switch notif;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Réglages");
        setContentView(R.layout.activity_settings);
        notif = (Switch) findViewById(R.id.settings_notifications);
        notif.setChecked(true);
        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //notif
                }
                else return;
                 //pas notif
            }
        });
    }


}
