package com.example.haryono.lowkost.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.haryono.lowkost.Adapter.SearchAdapter;
import com.example.haryono.lowkost.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchBarActivity extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> kostNameList;
    ArrayList<String> kostGenreList;
    ArrayList<String> userList;
    ArrayList<String> image_urlList;
    ArrayList<String> kostPriceList;
    ArrayList<String> kostPhoneList;
    //    ArrayList<String> profilePicList;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        kostNameList = new ArrayList<>();
        kostGenreList = new ArrayList<>();
        userList = new ArrayList<>();
        image_urlList = new ArrayList<>();
        kostPriceList = new ArrayList<>();
        kostPhoneList = new ArrayList<>();
//        profilePicList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                    * Clear the list when editText is empty
                    * */
                    kostNameList.clear();
                    kostGenreList.clear();
                    userList.clear();
                    image_urlList.clear();
                    kostPriceList.clear();
                    kostPhoneList.clear();
//                    profilePicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("photo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                * Clear the list for every new search
                * */
                kostNameList.clear();
                kostGenreList.clear();
                userList.clear();
                image_urlList.clear();
                kostPriceList.clear();
                kostPhoneList.clear();
//                profilePicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                * Search all users for matching searched string
                * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String kostName = snapshot.child("kostName").getValue(String.class);
                    String kostGenre = snapshot.child("kostGenre").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String image_url = snapshot.child("image_urlList").getValue(String.class);
                    String kostPrice = snapshot.child("kostPrice").getValue(String.class);
                    String kostPhone = snapshot.child("kostPhone").getValue(String.class);

//                    String profile_pic = snapshot.child("profile_pic").getValue(String.class);

                    if (kostName.toLowerCase().contains(searchedString.toLowerCase())) {
                        kostNameList.add(kostName);
                        kostGenreList.add(kostGenre);
                        userList.add(email);
                        image_urlList.add(image_url);
                        kostPriceList.add(kostPrice);
                        kostPhoneList.add(kostPhone);
//                        profilePicList.add(profile_pic);
                        counter++;
                    } else if (kostGenre.toLowerCase().contains(searchedString.toLowerCase())) {
                        kostNameList.add(kostName);
                        kostGenreList.add(kostGenre);
                        userList.add(email);
//                        profilePicList.add(profile_pic);
                        counter++;
                    }

                    /*
                    * Get maximum of 15 searched results only
                    * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(SearchBarActivity.this, kostNameList, kostGenreList, userList, image_urlList, kostPriceList, kostPhoneList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}