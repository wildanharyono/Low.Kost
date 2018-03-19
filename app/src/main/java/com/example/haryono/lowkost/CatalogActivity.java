package com.example.haryono.lowkost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CatalogActivity extends AppCompatActivity {


    DatabaseReference databaseKost;
    ListView listViewKost;
    List<Kost> kostList;

    public static final String ARTIST_NAME="artistname";
    public static final String ARTIST_ID="artistid";
    public static final String ARTIST_GENRE="artistgenre";
    public static final String ARTIST_LOKASI="artistlokasi";
    public static final String ARTIST_FASILITAS="artistfasilitas";
    public static final String ARTIST_HARGA="artistharga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        listViewKost = (ListView)findViewById(R.id.listViewCatalog);
        databaseKost = FirebaseDatabase.getInstance().getReference("kost");

        kostList = new ArrayList<>();

        listViewKost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Kost Kost = kostList.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), DetailEditActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, Kost.getKostId());
                intent.putExtra(ARTIST_NAME, Kost.getKostName());
                intent.putExtra(ARTIST_GENRE, Kost.getKostGenre());
                intent.putExtra(ARTIST_LOKASI, Kost.getKostLokasi());
                intent.putExtra(ARTIST_FASILITAS, Kost.getKostFasilitas());
                intent.putExtra(ARTIST_HARGA, String.valueOf(Kost.getKostHarga()));

                //starting the activity with intent
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseKost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kostList.clear();

                for (DataSnapshot artistSapshot : dataSnapshot.getChildren()){
                    Kost kost = artistSapshot.getValue(Kost.class);

                    kostList.add(kost);
                }

                KostList adapter = new KostList(CatalogActivity.this, kostList);
                listViewKost.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
