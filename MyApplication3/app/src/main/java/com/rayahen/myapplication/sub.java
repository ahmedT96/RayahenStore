package com.example.omar.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class sub extends AppCompatActivity {

    RecyclerView recyclerd_itel;
    RecyclerView.LayoutManager layoutManager;
    String GategoryID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subcat);

        recyclerd_itel = findViewById(R.id.list_subcat);
        recyclerd_itel.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerd_itel.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        if (getIntent() != null) {
            GategoryID = getIntent().getStringExtra("GategroyID");
        }
        if (!GategoryID.isEmpty() && GategoryID != null) {
            loadlistfood(GategoryID);
        }
    }

    private void loadlistfood(String gategoryID) {


    }
}

