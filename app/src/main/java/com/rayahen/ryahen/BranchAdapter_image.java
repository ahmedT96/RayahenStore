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

import java.util.List;

public class BranchAdapter_image extends RecyclerView.Adapter<BranchAdapter_image.ViewHolder> {


    private Context mCtx;
    private List<BrancImageModel> categoryModels;

    public BranchAdapter_image(Context mCtx, List<BrancImageModel> categoryModels) {
        this.mCtx = mCtx;
        this.categoryModels = categoryModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_item_branch_image, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BrancImageModel categoryModel = categoryModels.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(Constant.video_URL + categoryModel.getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_ditel_image);
        }

    }


}



