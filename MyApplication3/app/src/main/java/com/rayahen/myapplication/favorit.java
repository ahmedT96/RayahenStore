package com.example.omar.helpinghand;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class favorit extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    TextView txtTotal;
    Button btnplace;
    List<fav> favorit = new ArrayList<>();
    favoritAdapter adepter;
    Common common;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_favorit, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.listCart2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        loadListItem();

    }


    private void loadListItem() {

        favorit = new database(getActivity()).getFavorit();
        adepter = new favoritAdapter(favorit, getActivity());
        recyclerView.setAdapter(adepter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


    }
}


