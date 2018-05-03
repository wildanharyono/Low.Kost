//package com.example.haryono.lowkost;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AddKostanActivity extends AppCompatActivity {
//
//    TextView textViewKostanName;
//    SeekBar seekbarRating;
//    EditText editTextKostanName;
//    Button buttonAddKostan;
//
//    ListView listViewKostan;
//
//    DatabaseReference databaseKostan;
//
//    List <Kostan> kostans;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_kostan);
//
//
//        textViewKostanName = (TextView)findViewById(R.id.textViewKostanName);
//        editTextKostanName = (EditText)findViewById(R.id.editTextKostanName);
//        seekbarRating = (SeekBar)findViewById(R.id.seekbarRating);
//        buttonAddKostan = (Button)findViewById(R.id.buttonAddKostan);
//
//        listViewKostan = (ListView)findViewById(R.id.listViewKostan);
//
//
//        Intent intent = getIntent();
//
//        kostans = new ArrayList<>();
//        String id =intent.getStringExtra(MenuActivity.ARTIST_ID);
//        String name =intent.getStringExtra(MenuActivity.ARTIST_NAME);
//
//        textViewKostanName.setText(name);
//
//        databaseKostan = FirebaseDatabase.getInstance().getReference("kostan").child(id);
//
//
//        buttonAddKostan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveKostan();
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        databaseKostan.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                kostans.clear();
//
//                for(DataSnapshot kostanSnapshot : dataSnapshot.getChildren()){
//                    Kostan kostan = kostanSnapshot.getValue(Kostan.class);
//                    kostans.add(kostan);
//                }
//
//                KostanList kostanListAdapter = new KostanList(AddKostanActivity.this, kostans);
//                listViewKostan.setAdapter(kostanListAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void saveKostan(){
//        String kostanName = editTextKostanName.getText().toString().trim();
//        int rating = seekbarRating.getProgress();
//        if(!TextUtils.isEmpty(kostanName)){
//            String id = databaseKostan.push().getKey();
//
//            Kostan kostan = new Kostan(id, kostanName,rating);
//
//            databaseKostan.child(id).setValue(kostan);
//
//            Toast.makeText(this, "Tersimpan", Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(this, "gaboleh ksong", Toast.LENGTH_LONG).show();
//        }
//    }
//}
