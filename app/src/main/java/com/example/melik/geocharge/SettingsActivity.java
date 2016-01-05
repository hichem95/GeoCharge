package com.example.melik.geocharge;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Melik on 08/11/2015.
 */
public class SettingsActivity extends AppCompatActivity  {

    //Classe pour les Réglages

    private static final String PREF_FILE="fichier_preference";
    private Switch notif;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Réglages");
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(PREF_FILE, 0);
        boolean notifictaion = settings.getBoolean("notification", false);

        notif = (Switch) findViewById(R.id.settings_notifications);
        notif.setChecked(notifictaion);
        final Intent intent=new Intent(SettingsActivity.this,NotificationService.class);
        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//notif activé
                    Toast.makeText(getApplicationContext(), "Notification activé", Toast.LENGTH_SHORT).show();
                    startService(intent);
                } else {//pas notif
                    Toast.makeText(getApplicationContext(), "Notification désactivé", Toast.LENGTH_SHORT).show();
                    stopService(intent);
                }
            }
        });

        Button a_propos=(Button) findViewById(R.id.settings_about);
        a_propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about=new Intent(SettingsActivity.this,A_propos.class);
                startActivity(about);
            }
        });

        Button compte = (Button) findViewById(R.id.settings_account);
        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account=new Intent(Intent.ACTION_VIEW , Uri.parse("https://accounts.google.com/Login"));
                startActivity(account);
            }
        });

        Button help = (Button) findViewById(R.id.settings_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent help=new Intent(Intent.ACTION_VIEW , Uri.parse("http://geocharge.netne.net"));
                startActivity(help);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("notification", notif.isChecked());
        editor.apply();
    }





}
