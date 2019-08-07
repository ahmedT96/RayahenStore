package com.rayahen.ryahen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter_div extends RecyclerView.Adapter<OrderAdapter_div.OrderViewHolder> {


    private Context mCtx;
    private List<OrderDiv> orderDivs;

    public OrderAdapter_div(Context mCtx, List<OrderDiv> requestOrders) {
        this.mCtx = mCtx;
        this.orderDivs = requestOrders;
    }

    @Override
    public  OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_order_view_div, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter_div.OrderViewHolder holder, final int position) {

        final OrderDiv requestOrder=orderDivs.get(position);
        //loading the image

        holder.txtOrdername.setText(requestOrder.getName());
        holder.txtOrderprice.setText(requestOrder.getTotal());
        holder.txtOrderAddress.setText(requestOrder.getAdress());
        holder.id_order_div.setText(requestOrder.getId());
holder.accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        location();
        Intent inta=new Intent(mCtx,track_dirvaly.class);
        inta.putExtra("lat",orderDivs.get(position).lat);
        inta.putExtra("lang",orderDivs.get(position).lang);
        inta.putExtra("id",sharedprefmanger.getInstance(mCtx).getId());

        mCtx.startActivity(inta);
    }
});

holder.done.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        CatID(requestOrder.getId());

    }
});
    }
    private void CatID(final String id) {

        final List<OrderDiv> productModerls=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ordwediv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final String result=response.toString();
                Toast.makeText(mCtx, result, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        Requesthandeler.getInstance(mCtx).addToRequestQueue(stringRequest);
    }

    private void location() {
        LocationManager mlocManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener(mCtx);
        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) mlocListener);

    }

    @Override
    public int getItemCount() {
        return orderDivs.size();
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView txtOrdername,id_order_div,txtOrderprice,txtOrderAddress;
        Button calls,accept,done;

        CardView cardView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            txtOrdername = itemView.findViewById(R.id.Order_item_name_div);
            accept = itemView.findViewById(R.id.Accept_div);
            done = itemView.findViewById(R.id.done_div);
            txtOrderprice = itemView.findViewById(R.id.Order_item_price_div);
            txtOrderAddress = itemView.findViewById(R.id.Order_item_address_div);
            id_order_div = itemView.findViewById(R.id.id_order_div);


        }

    }


}