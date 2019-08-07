package com.rayahen.ryahen;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    private Context mCtx;
    private List<OrderDiv> orderDivs;

    public OrderAdapter(Context mCtx, List<OrderDiv> requestOrders) {
        this.mCtx = mCtx;
        this.orderDivs = requestOrders;
    }

    @Override
    public  OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_order_view, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, final int position) {

        final OrderDiv requestOrder=orderDivs.get(position);
        //loading the image

        holder.txtOrdername.setText(requestOrder.getBuild()+" - "+requestOrder.getFlat());
        holder.txtOrderprice.setText(requestOrder.getTotal()+" EGP");
        holder.txtOrderAddress.setText(requestOrder.getAdress());
        holder.txtOrderID.setText(requestOrder.getId());
        Toast.makeText(mCtx, requestOrder.getId(), Toast.LENGTH_SHORT).show();
        holder.tracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inta=new Intent(mCtx,order_track.class);
                inta.putExtra("lat",orderDivs.get(position).getLat());
                inta.putExtra("lang",orderDivs.get(position).getLang());

                mCtx.startActivity(inta);            }
        });
        holder.cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, R.string.call, Toast.LENGTH_SHORT).show();
               // CatID(requestOrder.getId());
            }
        });

    }
    private void CatID(final String id) {

        final List<OrderDiv> productModerls=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ordweCancel, new Response.Listener<String>() {
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

    @Override
    public int getItemCount() {
        return orderDivs.size();
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView txtOrdername,txtOrderID,txtOrderprice,txtOrderAddress;
Button tracks,cancels;
        CardView cardView;
        public OrderViewHolder(View itemView) {
            super(itemView);
            txtOrdername = itemView.findViewById(R.id.Order_item_name);
            tracks = itemView.findViewById(R.id.track);
            cancels = itemView.findViewById(R.id.cancel);


            txtOrderprice = itemView.findViewById(R.id.Order_item_price);
            txtOrderAddress = itemView.findViewById(R.id.Order_item_address);
            txtOrderID= itemView.findViewById(R.id.id_order_s);

        }

    }
}