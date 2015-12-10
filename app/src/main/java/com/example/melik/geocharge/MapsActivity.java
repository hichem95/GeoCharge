package com.example.melik.geocharge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View alertDialogView;

    String detailsText;
    CheckBox usb,ac;


    // ---------FNCT DE BASE -------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);  // active bouton localisation
    }


    // ------------ MENU ----------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // Comportement du bouton "Ajouter Bornes"
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                alertDialogView = getLayoutInflater().inflate(R.layout.dialog_add, null);
                dialog.setView(alertDialogView);
                dialog.setTitle(R.string.title_add);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Ajouter", new OkOnClickListener());
                dialog.setNegativeButton("Annuler", new CancelOnClickListener());
                dialog.create();
                dialog.show();
                return true;

            case R.id.action_favorite:
                // Comportement du bouton "Favoris"
                Intent fav = new Intent(MapsActivity.this, FavoriteActivity.class);
                startActivity(fav);
                return true;
            case R.id.action_settings:
                // Comportement du bouton "Réglages"
                Intent set = new Intent(MapsActivity.this, SettingsActivity.class);
                startActivity(set);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // -----------Action ADD BORN --------------

    private final class CancelOnClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "Ajout annuler", Toast.LENGTH_LONG).show();
        }
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            EditText editText = (EditText) alertDialogView.findViewById(R.id.details_add);
            usb= (CheckBox) alertDialogView.findViewById(R.id.type_USB_add);
            ac= (CheckBox) alertDialogView.findViewById(R.id.type_AC_add);
            LatLng pos = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
            detailsText = editText.getText().toString();


            if(detailsText.trim().equals("") || (!usb.isChecked()&& !ac.isChecked()))
                Toast.makeText(getApplicationContext(), "Vous devez entrer toutes les informations", Toast.LENGTH_SHORT).show();
            else {

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                        TextView type = (TextView) v.findViewById(R.id.type_infoWindow);
                        TextView details = (TextView) v.findViewById(R.id.details_infoWindow);
                        if (usb.isChecked() && ac.isChecked())
                            type.setText("USB/AC");
                        else if (usb.isChecked())
                            type.setText("USB ");
                        else if (ac.isChecked())
                            type.setText("AC");

                        details.setText(detailsText);
                        return v;
                    }

                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }
                });
                mMap.addMarker(new MarkerOptions().position(pos));


                }
            }
        }
    }

