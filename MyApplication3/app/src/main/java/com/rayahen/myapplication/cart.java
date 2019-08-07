package com.example.omar.helpinghand;

import android.content.DialogInterface;
import android.content.Intent;
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

public class cart extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    TextView txtTotal;
    Button btnplace,btnadress;
    List<Order> carts = new ArrayList<>();
    CartAdepter adepter;
    Common common;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_cart, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        txtTotal = view.findViewById(R.id.total_cart);
        btnadress = view.findViewById(R.id.btn_address);

        btnplace = view.findViewById(R.id.chackout);
        btnplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
        loadListItem();
        btnadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);

            }
        });

    }

    private void showAlert() {
        AlertDialog.Builder alertdialg=new AlertDialog.Builder(getActivity());
        alertdialg.setTitle("One more");
        alertdialg.setMessage("Enter your adaress : ");

        final EditText editText=new EditText(getActivity());
        LinearLayout.LayoutParams ip =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(ip);
        alertdialg.setView(editText);
        alertdialg.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertdialg.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //request request1=new request(Common.currentuser.getPhone(),Common.currentuser.getName(),
                    //    editText.getText().toString(),txtTotal.getText().toString(),carts);

                //requests.child(String.valueOf(System.currentTimeMillis())).setValue(request1);
                new database(getContext()).cleenCart();
                Toast.makeText(getContext(),"thank you",Toast.LENGTH_LONG).show();
            }

        });
        alertdialg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertdialg.show();
    }

    private void loadListItem() {

        carts = new database(getActivity()).getCart();
        adepter = new CartAdepter(carts, getActivity());
        recyclerView.setAdapter(adepter);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        int total = 0;
      for (Order order : carts)
          total += (Integer.parseInt(order.getPrice()));
     //   Locale locale = new Locale("en", "US");
        StringBuilder sb = new StringBuilder();
        sb.append(total+" EGP");

        //String fmt = NumberFormat.getCurrencyInstance(total);
      txtTotal.setText(sb.toString());

    }
}


