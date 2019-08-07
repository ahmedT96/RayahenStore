package com.example.omar.helpinghand;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txt_cart_name,txt_price;
    public ImageView imag_cart_count;
    public ImageView image_product;

    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View view) {

    }


    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name= itemView.findViewById(R.id.cart_item_name);
        txt_price= itemView.findViewById(R.id.cart_item_price);
        imag_cart_count= itemView.findViewById(R.id.cart_Item_count);
        image_product= itemView.findViewById(R.id.image_product);



    }
}

public class CartAdepter extends RecyclerView.Adapter<CartViewHolder>{
    private List<Order> listData=new ArrayList<>();
    private Context context;

    public CartAdepter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View itemView =inflater.inflate(R.layout.layout_item_cart,parent,false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
     TextDrawable drawable =TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
     holder.imag_cart_count.setImageDrawable(drawable);
        Locale locale=new Locale("en","US");
        //NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);
//     int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(listData.get(position).getPrice());
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        Glide.with(context)
                .load(listData.get(position).getImage())
                .into(holder.image_product);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
