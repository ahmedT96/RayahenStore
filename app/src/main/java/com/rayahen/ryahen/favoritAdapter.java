package com.rayahen.ryahen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class favoritAdapter extends RecyclerView.Adapter<favViewHolder>{
    private List<FavoritModel> listData=new ArrayList<>();
    private Context context;

    public favoritAdapter(List<FavoritModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public favViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item, null);
        return new favViewHolder(view);
    }

    @Override
    public void onBindViewHolder(favViewHolder holder, final int position) {

        //NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);
//     int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.textViewPrice.setText(listData.get(position).getPrice());
        holder.textViewTitle.setText(listData.get(position).getTitle());
        Glide.with(context)
                .load(Constant.vide_URL+listData.get(position).getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listData.get(position).getKind()==0) {
                    Intent intent = new Intent(context, Product_ditals.class);
                    intent.putExtra("id", listData.get(position).getId());
                    intent.putExtra("name", listData.get(position).getTitle());
                    intent.putExtra("price", listData.get(position).getPrice());
                    intent.putExtra("image", listData.get(position).getImage());
                    intent.putExtra("discreption", listData.get(position).getDescrepton());
                    intent.putExtra("kind", listData.get(position).getKind());
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, settingActivty.class);
                    intent.putExtra("id", listData.get(position).getId());
                    intent.putExtra("name", listData.get(position).getTitle());
                    intent.putExtra("price", listData.get(position).getPrice());
                    intent.putExtra("image", listData.get(position).getImage());
                    intent.putExtra("kind", listData.get(position).getKind());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}

class favViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


    TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
    ImageView imageView;
    CardView cardView;



    @Override
    public void onClick(View view) {

    }


    public favViewHolder(View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.text_product);
        textViewPrice = itemView.findViewById(R.id.text_price);
        cardView = itemView.findViewById(R.id.cards);
        imageView = itemView.findViewById(R.id.img_product);
    }
}
