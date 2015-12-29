package com.example.melik.geocharge;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Melik on 25/12/2015.
 */
public class Borne  {
        private String type,details,nom;
        private double longitude,latitude;
        private Marker uneBorne;



        public Borne(String unType, String desDetails, double latitude,double longitude){
            this.type=unType;
            this.details=desDetails;
            this.latitude=latitude;
            this.longitude=longitude;
        }

        public Marker ajouterBorneMap(GoogleMap uneMap){
            this.uneBorne=uneMap.addMarker(new MarkerOptions().title(type).snippet(details).position(new LatLng(this.latitude,this.longitude)));
            return this.uneBorne;
        }
        public void ajouterBorneBDD(Database db){
            db.ajouterBorne(this);
        }

        public String getType() {
            return type;
        }

        public String getDetails() {
            return details;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getNom(){
            return nom;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }


    @Override
    public String toString() {
        return "Borne{" +
                "type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", nom='" + nom + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
