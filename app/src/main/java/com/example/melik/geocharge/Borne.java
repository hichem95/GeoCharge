package com.example.melik.geocharge;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Melik on 25/12/2015.
 */
public class Borne implements GoogleMap.InfoWindowAdapter {
        private String type,details;
        private LatLng position;
        private Marker uneBorne;

        public Borne(GoogleMap uneMap, String unType, String desDetails, LatLng unePosition){
            this.type=unType;
            this.details=desDetails;
            this.position=unePosition;
            this.uneBorne=uneMap.addMarker(new MarkerOptions().title(type).snippet(details).position(position));

        }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = View.inflate()
        TextView type = (TextView) v.findViewById(R.id.type_infoWindow);
        TextView details = (TextView) v.findViewById(R.id.details_infoWindow);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
