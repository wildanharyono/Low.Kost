package com.example.haryono.lowkost.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
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
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    //    ArrayList<String> profilePicList;
    SearchAdapter searchAdapter;
    BottomNavigationView btmNav2;
    SearchBarActivity search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btmNav2 = (BottomNavigationView) findViewById(R.id.btm_nav);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        fullNameList = new ArrayList<>();
        userNameList = new ArrayList<>();
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
                    fullNameList.clear();
                    userNameList.clear();
//                    profilePicList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });

        btmNav2.setSelectedItemId(R.id.action_money);
        btmNav2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home :
                        startActivity(new Intent(SearchBarActivity.this, MainActivity.class));
                        break;
                    case R.id.action_star :
                        startActivity(new Intent(SearchBarActivity.this, AddPhotoActivity.class));
                        break;
                    case R.id.action_money :
                        search = new SearchBarActivity();
                        break;
                }
                return true;
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
                fullNameList.clear();
                userNameList.clear();
//                profilePicList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                * Search all users for matching searched string
                * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_name = snapshot.child("kostName").getValue(String.class);
                    String user_name = snapshot.child("kostGenre").getValue(String.class);
//                    String profile_pic = snapshot.child("profile_pic").getValue(String.class);

                    if (full_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
//                        profilePicList.add(profile_pic);
                        counter++;
                    } else if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
//                        profilePicList.add(profile_pic);
                        counter++;
                    }

                    /*
                    * Get maximum of 15 searched results only
                    * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(SearchBarActivity.this, fullNameList, userNameList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
