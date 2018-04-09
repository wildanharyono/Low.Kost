//package com.example.haryono.lowkost.Fragment;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.haryono.lowkost.Model.PhotoModel;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import com.example.haryono.lowkost.R;
//import com.example.haryono.lowkost.Adapter.PhotoAdapter;
//import com.example.haryono.lowkost.Config.Constant;
//import com.example.haryono.lowkost.Model.CommentModel;
//import com.example.haryono.lowkost.Model.PhotoModel;
//
//public class SearchFragment extends Fragment {
//    //deklarasi variable dan views
//    private static final String KEY_PARAM = "key_param";
//    private ArrayList<PhotoModel> photoList;
//    ArrayList<String> fullNameList;
//    private PhotoAdapter mAdapter;
//    DatabaseReference databaseReference;
//    FirebaseUser firebaseUser;
//
//    @BindView(R.id.rvFoto) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
//            RecyclerView rvFoto;
//    @BindView(R.id.search_edit_text) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
//            EditText search_edit_text;
//    @BindView(R.id.swipeRefresh) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
//            SwipeRefreshLayout swipeRefresh;
//
//    public SearchFragment() {
//        // Required empty public constructor
//    }
//
//    //method untuk menerima data yang dikirimkan/passing dari activity
//    public static Bundle arguments(String param) {
//        return new Bundler()
//                .putString(KEY_PARAM, param)
//                .get();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    String menu;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        ButterKnife.bind(this, view); //binding ButterKnife dengan fragment
//        Bundle bundle = getArguments(); //mengambil nilai/data yang didapatkan dari activity
//        menu = bundle.getString(KEY_PARAM);
//
//        photoList = new ArrayList<>();
//        fullNameList = new ArrayList<>();
//
//        //konfig recyclerview layout manager dan adapter
//        rvFoto.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mAdapter = new PhotoAdapter(photoList, getActivity());
//        rvFoto.setAdapter(mAdapter);
//        loadData();
//
//        // refresh saat di load ulang dengan pull to refresh
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//            }
//        });
//        return view;
//
//
//        search_edit_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    loadData(s.toString());
//                } else {
//                    /*
//                    * Clear the list when editText is empty
//                    * */
//                    fullNameList.clear();
//                    rvFoto.removeAllViews();
//                }
//            }
//        });
//
//
//    }
//
//    int commentCount = 0;
//
//    //method untuk loaddata photo dari firebase
//    private void loadData(final String searchedString) {
//        if (menu.equals("pencarian")) { //semua foto berdasarkan yang terbaru
//
//            swipeRefresh.setRefreshing(true);
//            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    photoList.clear();
//                    commentCount = 0;
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//
//                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
//                        String uid = ds.getKey();
//                        String full_name = ds.child("full_name").getValue(String.class);
////                        PhotoModel model = ds.getValue(PhotoModel.class);
////                        photoList.add(model); //dimasukkan list photo
//
//
//                        if (full_name.toLowerCase().contains(searchedString.toLowerCase())) {
//                            fullNameList.add(full_name);
//                            commentCount++;
//                        }
//                        mAdapter.notifyDataSetChanged(); //refresh adapter
//                    }
//                    swipeRefresh.setRefreshing(false);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w("", "Failed to read value.", error.toException());
//                    //showProgress(false);
//                }
//            });
//        }
//    }
//}