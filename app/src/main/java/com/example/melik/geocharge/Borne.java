package com.example.melik.geocharge;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Melik on 25/12/2015.
 */
public class Borne  {
        private String type,details,nom;
        private double longitude,latitude;
        private Marker uneBorne;
        private boolean selected;



        public Borne(String unType, String desDetails, double latitude,double longitude){
            this.type=unType;
            this.details=desDetails;
            this.latitude=latitude;
            this.longitude=longitude;
            this.selected=false;
        }

        public Marker ajouterBorneMap(GoogleMap uneMap){
            this.uneBorne=uneMap.addMarker(new MarkerOptions().position(new LatLng(this.latitude,this.longitude)));
            return this.uneBorne;
        }
        public void ajouterBorneBDD(Database db){
            db.ajouterBorne(this);
        }

        public void supprimerBorne(Database db){
            this.uneBorne.remove();
            db.supprimerBorne(this);
            ArrayList<Borne> arrayBorne=db.recupereBorneFavoris();
            ListIterator<Borne> l=arrayBorne.listIterator();
            while(l.hasNext()){
                Borne b=l.next();
                if(this.latitude==b.latitude && this.longitude==b.longitude){
                    this.supprimerBorneFavoris(db);
                }
            }
        }

        public void ajouterBorneFavoris(String nom,Database db){
            this.setNom(nom);
            db.ajouterBorneFavoris(this);
        }

        public void supprimerBorneFavoris(Database db){
            db.supprimerBorneFavoris(this);
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

        public Marker getUneBorne() { return uneBorne;    }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }


    public void setUneBorne(Marker uneBorne) {
        this.uneBorne = uneBorne;
    }

    @Override
    public String toString() {
        return "Borne{" +
                "type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", nom='" + nom + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
