package com.example.haryono.lowkost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ARTIST_NAME="artistname";
    public static final String ARTIST_ID="artistid";
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    Button buttonAdd;
    Spinner sipinnerGenres;
    EditText editTextName;

    DatabaseReference databaseKost;

    ListView listViewArtists;
    List<Kost>kostList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseKost = FirebaseDatabase.getInstance().getReference("kost");

        editTextName = (EditText)findViewById(R.id.editTextName);
        buttonAdd = (Button)findViewById(R.id.buttonAddArtist);
        sipinnerGenres = (Spinner)findViewById(R.id.spinnerGenres);


        listViewArtists= (ListView)findViewById(R.id.listViewArtists);

        kostList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addArtist();
            }
        });

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Kost Kost = kostList.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AddKostanActivity.class);

                //putting artist name and id to intent
                intent.putExtra(ARTIST_ID, Kost.getArtistId());
                intent.putExtra(ARTIST_NAME, Kost.getArtistName());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
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

                KostList adapter = new KostList(MenuActivity.this, kostList);
                listViewArtists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addArtist(){
        String name = editTextName.getText().toString().trim();
        String genre = sipinnerGenres.getSelectedItem().toString();
        if (!TextUtils.isEmpty(name)) {
            String id = databaseKost.push().getKey();

            //creating an Artist Object
            Kost artist = new Kost(id, name, genre);

            //Saving the Artist
            databaseKost.child(id).setValue(artist);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();

        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}