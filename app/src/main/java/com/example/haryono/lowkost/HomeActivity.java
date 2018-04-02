package com.example.haryono.lowkost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.haryono.lowkost.Activity.SearchActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onEdit(View view){
        Intent send = new Intent(this, MenuActivity.class);
        startActivity(send);
    }
    public void onSearch(View view){
        Intent send = new Intent(this, CatalogActivity.class);
        startActivity(send);
    }
    public void onSearch2(View view){
        Intent send = new Intent(this, Catalog2Activity.class);
        startActivity(send);
    }
    public void onSearch3(View view){
        Intent send = new Intent(this, SearchActivity.class);
        startActivity(send);
    }

}
