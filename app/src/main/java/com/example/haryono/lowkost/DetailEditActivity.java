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
//public class DetailEditActivity extends AppCompatActivity {
//
//    TextView textViewKostName;
//    TextView textViewKostGenre;
//    TextView textViewKostLokasi;
//    TextView textViewKostFasilitas;
//    TextView textViewKostHarga;
//    Button buttonAddKostan;
//
//    ListView listViewKostan;
//
//    DatabaseReference databaseKostan;
//
//    List<Kostan> kostans;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_edit);
//
//
//        textViewKostName = (TextView)findViewById(R.id.textViewName);
//        textViewKostGenre = (TextView)findViewById(R.id.textViewGenre);
//        textViewKostLokasi = (TextView)findViewById(R.id.textViewLokasi);
//        textViewKostFasilitas = (TextView)findViewById(R.id.textViewFasilitas);
//        textViewKostHarga = (TextView)findViewById(R.id.textViewHarga);
//        buttonAddKostan = (Button)findViewById(R.id.buttonAddKostan);
//
//
//        Intent intent = getIntent();
//
//        String id =intent.getStringExtra(MenuActivity.ARTIST_ID);
//        String name =intent.getStringExtra(MenuActivity.ARTIST_NAME);
//        String genre =intent.getStringExtra(MenuActivity.ARTIST_GENRE);
//        String lokasi =intent.getStringExtra(MenuActivity.ARTIST_LOKASI);
//        String fasilitas =intent.getStringExtra(MenuActivity.ARTIST_FASILITAS);
//        String harga =intent.getStringExtra(MenuActivity.ARTIST_HARGA);
//
//        textViewKostName.setText(name);
//        textViewKostGenre.setText(genre);
//        textViewKostLokasi.setText(lokasi);
//        textViewKostFasilitas.setText(fasilitas);
//        textViewKostHarga.setText(harga);
//
//    }
//
//}