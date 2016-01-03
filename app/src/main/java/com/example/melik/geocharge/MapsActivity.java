package com.example.melik.geocharge;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.ListIterator;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private View alertDialogView;
    private Database db;

    String detailsText;
    CheckBox usb,ac;


    // ---------FNCT DE BASE -------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FragmentTransaction f = getFragmentManager().beginTransaction();
        f.hide(getFragmentManager().findFragmentById(R.id.details_frag));
        f.commit();

        this.db=new Database(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);  // active bouton localisation
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                mMap.setOnMyLocationChangeListener(null);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                FragmentTransaction f = getFragmentManager().beginTransaction();
                DetailsFragment df = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details_frag);
                TextView type = (TextView) df.getView().findViewById(R.id.type_infoWindow);
                TextView details = (TextView) df.getView().findViewById(R.id.details_infoWindow);
                type.setText(marker.getTitle());
                details.setText(marker.getSnippet());
                f.show(getFragmentManager().findFragmentById(R.id.details_frag));
                f.commit();
                return false;
            }
        });
        this.init_bornes();
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
            detailsText = editText.getText().toString();
            String type="";

            if(detailsText.trim().equals("") || (!usb.isChecked()&& !ac.isChecked()))
                Toast.makeText(getApplicationContext(), "Vous devez entrer toutes les informations", Toast.LENGTH_SHORT).show();
            else {

                        if (usb.isChecked() && ac.isChecked())
                            type="USB/AC";
                        else if (usb.isChecked())
                            type="USB";
                        else if (ac.isChecked())
                            type="AC";
                Borne b=new Borne(type,detailsText,mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                b.ajouterBorneMap(mMap);
                b.ajouterBorneBDD(db);

                    }
            }
    }

    public void init_bornes(){
        ArrayList<Borne> arr=db.getAllBorne();
        ListIterator<Borne> l=arr.listIterator();
        while(l.hasNext()){
            Borne b = l.next();
            b.ajouterBorneMap(mMap);
        }
    }


}

