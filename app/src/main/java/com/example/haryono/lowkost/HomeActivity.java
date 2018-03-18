package com.example.haryono.lowkost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
}
