package com.example.haryono.lowkost.Activity;

/**
 * Created by haryono on 4/2/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haryono.lowkost.Adapter.CommentAdapter;
import com.example.haryono.lowkost.Config.Constant;
import com.example.haryono.lowkost.Kost;
import com.example.haryono.lowkost.Kostan;
import com.example.haryono.lowkost.Model.CommentModel;
import com.example.haryono.lowkost.Model.PhotoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.haryono.lowkost.R;
import com.example.haryono.lowkost.Adapter.CommentAdapter;
import com.example.haryono.lowkost.Config.Constant;
import com.example.haryono.lowkost.Model.CommentModel;
import com.example.haryono.lowkost.Model.PhotoModel;

public class PhotoDetailEditActivity extends AppCompatActivity {
    //Deklarasi View
    List<Kostan> kostans;
    @BindView(R.id.imgEvent) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            ImageView imgEvent;
    @BindView(R.id.tvDescription) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextView tvDescription;
    @BindView(R.id.tvLokasi) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextView tvLokasi;
    @BindView(R.id.rvKomentar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            RecyclerView rvKomentar;
    @BindView(R.id.etKomentar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            TextInputEditText etKomentar;
    @BindView(R.id.btnKirim) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            Button btnKirim;
    @BindView(R.id.toolbar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.btnEdit)Button btnEdit;
    private ArrayList<CommentModel> commentList; //arraylist untuk menyimpan hasil load komentar
    private CommentAdapter mAdapter;
    Context contex;
    List<PhotoModel>kostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail_edit);
        ButterKnife.bind(this); //Binding ButterKnife pada activity ini
//        setSupportActionBar(toolbar);
        displayHomeAsUpEnabled();
        commentList = new ArrayList<>();

        //setting layout dari recyclerview dan adapter
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKomentar.setLayoutManager(llManager);
        mAdapter = new CommentAdapter(commentList);
        rvKomentar.setAdapter(mAdapter);

        loadIntent();

//        btnEdit.setOnClickListener(new AdapterView.setOnClickListener() {
//            @Override
//            public boolean OnClickListener(AdapterView<?> adapterView, View view, int i, long l) {
//                PhotoModel PhotoModel = kostList.get(i);
//                showUpdateDialog(PhotoModel.getKey(), PhotoModel.getImage_url(), PhotoModel.getTitle(), PhotoModel.getDesc(), PhotoModel.getLokasi(), PhotoModel.getName(), PhotoModel.getEmail());
//                return true;
//            }
//        });


        //          cardViewKost.setOnClickListener((View.OnClickListener) this);
//          cardViewKost.setOnLongClickListener(new View.OnLongClickListener() {
//              @Override
//              public boolean onLongClick(View view) {
//                  PhotoModel kost = kostList.get(1);
//                  showUpdateDialog(kost.getKey(), kost.getImage_url(), kost.title, kost.desc, kost.getLokasi(), kost.name, kost.email);
//                  return true;
//              }

//              @Override
//            public boolean onLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                PhotoModel kost = kostList.get(i);
//                showUpdateDialog(kost.getKey(), kost.getImage_url(), kost.title, kost.desc, kost.getLokasi(), kost.name, kost.email);
//                return true;
//            }
//        });


    }

    PhotoModel photo;

    public void setBtnEdit(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_dialog);
        dialog.show();

    }



    private void loadIntent() { //mengambil value yang dipassing dari selected photo di MyPhotoAdapter
        if (getIntent().getExtras() != null) {
            photo = (PhotoModel) getIntent().getSerializableExtra("photoData"); //ambil model yg dipassing
            Picasso.get().load(photo.getImage_url()).into(imgEvent); //load gambar menggukanan picasso
            tvDescription.setText(photo.getDesc() + "\n" + photo.getLokasi() + "\nby: " + photo.getName());
//            tvLokasi.setText(photo.getLokasi() + "\nby: " + photo.getName());
            setTitle(photo.getTitle()); //set judul toolbar
            loadComment(); //load comment
        }
    }

    //method ini berfungsi untuk load komentar dari key/photo yang dipilih
    private void loadComment() {
        Constant.refPhoto.child(photo.getKey()).child("commentList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        commentList.clear();
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            CommentModel model = ds.getValue(CommentModel.class);
                            commentList.add(model); //dimasukkan kedalam list
                            mAdapter.notifyDataSetChanged(); //refresh data
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("", "Failed to read value.", error.toException());
                        //showProgress(false);
                    }
                });
    }

    //menampilkan tombol back button diatas kiri
    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //method untuk handling btnKirim
    @OnClick(R.id.btnKirim)
    public void kirim() {
        //validasi kosong
        if (etKomentar.getText().toString().isEmpty()) {
            Toast.makeText(this, "Harap isi komentar", Toast.LENGTH_SHORT).show();
            return;
        }

        //Insert atau push data komentar ke firebase
        Constant.refPhoto.child(photo.getKey()).child("commentList").push().setValue(new CommentModel(
                Constant.currentUser.getEmail().split("@")[0],
                Constant.currentUser.getEmail(),
                etKomentar.getText().toString()
        ));

        etKomentar.setText("");
    }

    //handler jika back button di klik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    private void showUpdateDialog(final String key, final String image_url, String title, String desc, String lokasi, String name, final String email) {
        AlertDialog.Builder dialogBulder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBulder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextFasilitas = (EditText) dialogView.findViewById(R.id.editTextFasilitas);
        final EditText editTextLokasi = (EditText) dialogView.findViewById(R.id.editTextLokasi);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBulder.setTitle("Updateing Kost" + title);

        final AlertDialog alertDialog = dialogBulder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String title = editTextName.getText().toString().trim();
                String lokasi = editTextLokasi.getText().toString().trim();
                String desc = editTextFasilitas.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    editTextName.setError("isi namanya");
                    return;
                }

                updateKost(key,  image_url, title, desc, lokasi, name, email );

                alertDialog.dismiss();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKost(key);
            }
        });
    }

    private void deleteKost(String artistId) {
        DatabaseReference drKost = FirebaseDatabase.getInstance().getReference("photo").child(artistId);
        DatabaseReference drKostan = FirebaseDatabase.getInstance().getReference("commentList").child(artistId);

        drKost.removeValue();
        drKostan.removeValue();

        Toast.makeText(this, "berhasil di dellete", Toast.LENGTH_LONG).show();
    }

    private boolean updateKost(String key, String image_url, String title, String desc, String lokasi, String name, String email){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("photo").child(key);

        PhotoModel kost = new PhotoModel(key, image_url, title, desc, lokasi,name, email );

        databaseReference.setValue(kost);

        Toast.makeText(this, "berhasil", Toast.LENGTH_LONG).show();

        return true;
    }
}