package com.example.melik.geocharge;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

public class DetailsFragment extends Fragment{

    private Borne uneBorne;
    private Database uneDb;
    private GoogleMap uneMap;
    private View favorisDialogView;

    public DetailsFragment(){
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_details_fragment, container, false);

        ImageButton close = (ImageButton) view.findViewById(R.id.close_details);
        ImageButton fav = (ImageButton) view.findViewById(R.id.favorite_infoWindow);
        ImageButton itin = (ImageButton) view.findViewById(R.id.itineraire_infoWindow);
        ImageButton report = (ImageButton) view.findViewById(R.id.report_infowindow);
        ImageButton delete=(ImageButton) view.findViewById(R.id.delete_infowindow);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                favorisDialogView = inflater.inflate(R.layout.dialog_favorite, null);
                dialog.setView(favorisDialogView);
                dialog.setTitle("Ajoutez aux favoris");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Ajouter", new OkOnClickListener());
                dialog.setNegativeButton("Annuler", new CancelOnClickListener());
                dialog.create();
                dialog.show();
                getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmation =new AlertDialog.Builder(getActivity());
                confirmation.setTitle("Supprimer");
                confirmation.setMessage("Etes-vous sur ?");
                confirmation.setCancelable(false);
                confirmation.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uneBorne.supprimerBorne(uneDb);
                    }
                });
                confirmation.setNegativeButton("Annuler", new CancelOnClickListener());
                confirmation.create();
                confirmation.show();
                getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
            }
        });

        itin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + uneMap.getMyLocation().getLatitude() + "," + uneMap.getMyLocation().getLongitude() + "&daddr=" + uneBorne.getLatitude() + "," + uneBorne.getLongitude()));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Votre signalement a bien été pris en compte", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
            }
        });
        return view;
    }

    public void setUneBorne(Borne uneBorne) {
        this.uneBorne = uneBorne;
    }

    public void setUnDb(Database unDb) {
        this.uneDb = unDb;
    }

    public void setUneMap(GoogleMap uneMap) {
        this.uneMap = uneMap;
    }

    private final class CancelOnClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            EditText editText = (EditText) favorisDialogView.findViewById(R.id.name_favorite);
            String nom= editText.getText().toString();
            if(nom.trim().equals("")){
                Toast.makeText(getContext(), "Ajout annuler", Toast.LENGTH_LONG).show();
            }
            else{
                uneBorne.ajouterBorneFavoris(nom,uneDb);
                Toast.makeText(getActivity(), "Borne Ajouter", Toast.LENGTH_LONG).show();

            }

        }
    }
}