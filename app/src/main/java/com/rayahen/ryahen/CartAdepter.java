package com.rayahen.ryahen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txt_cart_name,txt_price;
    public TextView cart_count;
    Button increment,decremnt;
    public ImageView image_product,remove;

    @Override
    public void onClick(View view) {

    }


    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name= itemView.findViewById(R.id.cart_item_name);
        txt_price= itemView.findViewById(R.id.cart_item_price);
        remove= itemView.findViewById(R.id.remove);


        image_product= itemView.findViewById(R.id.image_product);



    }
}

public class CartAdepter extends RecyclerView.Adapter<CartViewHolder>{
    private List<Order> listData=new ArrayList<>();
    private Context context;
    int quantity;

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
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        //TextDrawable drawable =TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        //holder.cart_count.setText(listData.get(position).getQuantity());
        quantity=Integer.parseInt(listData.get(position).getQuantity());
        final int[] totalprice = new int[1];
        Locale locale=new Locale("en","US");
        //NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);
//     int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(listData.get(position).getPrice() +" EGP");
        holder.txt_cart_name.setText(listData.get(position).getProductName());
        Glide.with(context)
                .load(Constant.vide_URL+listData.get(position).getImage())
                .into(holder.image_product);
        final database  localdb=new database(context);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localdb.removeToCart(listData.get(position).getProductId());
                Toast.makeText(context, R.string.removedCart, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,Cart.class);
                context.startActivity(intent);
                if (new database(context).IsCartEmpty())
                {
                    Intent ints=new Intent(context,Home.class);
                    context.startActivity(ints);
                }


            }
        });

    }



    @Override
    public int getItemCount() {
        return listData.size();
    }
}
