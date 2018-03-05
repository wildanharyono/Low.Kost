package com.example.haryono.lowkost;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
    //objek autentikasi firebase
    private FirebaseAuth firebaseAuth;

    //melihat objek
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

        //listener untun button add
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

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kost kost = kostList.get(i);
                showUpdateDialog(kost.getArtistId(), kost.getArtistName());
                return true;
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

    private void showUpdateDialog(final String artistId, String artistName){
        AlertDialog.Builder dialogBulder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final  View dialogView= inflater.inflate(R.layout.update_dialog, null);

        dialogBulder.setView(dialogView);


        final EditText editTextName = (EditText)dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerGenres = (Spinner)dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonDelete = (Button)dialogView.findViewById(R.id.buttonDelete);

        dialogBulder.setTitle("Updating Artist"+ artistName);

        final AlertDialog alertDialog = dialogBulder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name = editTextName.getText().toString().trim();
            String genre = spinnerGenres.getSelectedItem().toString();

            if(TextUtils.isEmpty(name)){
                editTextName.setError("isi namanya");
                return;
                }

                updateKost(artistId, name, genre);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKost(artistId);
            }
        });


    }

    private void deleteKost(String artistId) {
        DatabaseReference drKost = FirebaseDatabase.getInstance().getReference("kost").child(artistId);
        DatabaseReference drKostan = FirebaseDatabase.getInstance().getReference("kostan").child(artistId);

        drKost.removeValue();
        drKostan.removeValue();

        Toast.makeText(this, "berhasil di dellete", Toast.LENGTH_LONG).show();
    }

    private boolean updateKost(String id, String name, String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kost").child(id);

        Kost kost = new Kost(id, name, genre);

        databaseReference.setValue(kost);

        Toast.makeText(this, "berhasil", Toast.LENGTH_LONG).show();

        return true;
    }
}