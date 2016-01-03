package com.example.melik.geocharge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<Borne> listPoint;
    private ArrayAdapter adaptListe;
    private ListView liste;
    public static double lat;
    public static double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        displayListView();
    }



    public void ajoutListe(){
        adaptListe.notifyDataSetChanged();
    }

    public void supprimer(int id){
        listPoint.remove(id);
    }

    private void displayListView() {

        //Array list of countries
        listPoint = new ArrayList<Borne>();
        //create an ArrayAdaptar from the String Array
        adaptListe = new MyCustomAdapter(this, R.layout.fav_content, listPoint);
        liste = (ListView) findViewById(R.id.listFavoris);
        // Assign adapter to ListView
        liste.setAdapter(adaptListe);


        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Borne fav = (Borne) parent.getItemAtPosition(position);
                Intent i = new Intent(FavoriteActivity.this, MapsActivity.class);
                i.putExtra("Lat", fav.getLatitude());
                i.putExtra("Lon", fav.getLongitude());
                setResult(RESULT_OK,i);
                finish();
                /*Toast.makeText(getApplicationContext(),
                        "Clique sur  " + fav.getNom(),
                        Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Borne> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Borne> countryList) {
            super(context, textViewResourceId, countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.fav_content, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Borne country = (Borne) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                cb.getText() +
                                        " sélectionné ",
                                Toast.LENGTH_SHORT).show();*/
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Borne fav = (Borne) listPoint.get(position);
            holder.code.setText(" (" +  fav.getDetails() + ")");
            holder.name.setText(fav.getNom());
            holder.name.setChecked(fav.isSelected());
            holder.name.setTag(fav);

            return convertView;

        }

    }

    public boolean noItemSelected(){
        for(int i = 0 ; i < listPoint.size() ; i++){
            if(listPoint.get(i).isSelected())
                return false;
        }
        return true;
    }
}