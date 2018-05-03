//package com.example.haryono.lowkost;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class Catalog2Activity extends AppCompatActivity implements
//        CatalogAdapter.FirebaseDataListener {
//
//
//    private DatabaseReference database;
//    private RecyclerView rvView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Kost> daftarBarang;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        /**
//         * Mengeset layout
//         */
//        setContentView(R.layout.activity_catalog2);
//
//        /**
//         * Inisialisasi RecyclerView & komponennya
//         */
//        rvView = (RecyclerView) findViewById(R.id.rv_main);
//        rvView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        rvView.setLayoutManager(layoutManager);
//
//        /**
//         * Inisialisasi dan mengambil Firebase Database Reference
//         */
//        database = FirebaseDatabase.getInstance().getReference();
//
//        /**
//         * Mengambil data barang dari Firebase Realtime DB
//         */
//        database.child("kost").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                /**
//                 * Saat ada data baru, masukkan datanya ke ArrayList
//                 */
//                daftarBarang = new ArrayList<>();
//                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                    /**
//                     * Mapping data pada DataSnapshot ke dalam object Barang
//                     * Dan juga menyimpan primary key pada object Barang
//                     * untuk keperluan Edit dan Delete data
//                     */
//                    Kost barang = noteDataSnapshot.getValue(Kost.class);
//                    barang.setKostId(noteDataSnapshot.getKey());
//
//                    /**
//                     * Menambahkan object Barang yang sudah dimapping
//                     * ke dalam ArrayList
//                     */
//                    daftarBarang.add(barang);
//                }
//
//                /**
//                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
//                 * dan mengeset Adapter ke dalam RecyclerView
//                 */
//                adapter = new CatalogAdapter(daftarBarang, Catalog2Activity.this);
//                rvView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                /**
//                 * Kode ini akan dipanggil ketika ada error dan
//                 * pengambilan data gagal dan memprint error nya
//                 * ke LogCat
//                 */
//                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
//            }
//        });
//    }
//
//    public static Intent getActIntent(Activity activity){
//        return new Intent(activity, Catalog2Activity.class);
//    }
//
//    @Override
//    public void onDeleteData(Kost barang, int position) {
//        /**
//         * Kode ini akan dipanggil ketika method onDeleteData
//         * dipanggil dari adapter lewat interface.
//         * Yang kemudian akan mendelete data di Firebase Realtime DB
//         * berdasarkan key barang.
//         * Jika sukses akan memunculkan Toast
//         */
//        if(database!=null){
//            database.child("kost").child(barang.getKostId()).removeValue().
//                    addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(Catalog2Activity.this,"Data berhasil dihapus",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//        }
//    }
//}
