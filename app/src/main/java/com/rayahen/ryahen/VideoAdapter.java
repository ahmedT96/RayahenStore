package com.rayahen.ryahen;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private Context mCtx;
    private List<Video> mVideos;

    public VideoAdapter(Context mCtx, List<Video> categoryModels) {
        this.mCtx = mCtx;
        this.mVideos = categoryModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.video_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Video categoryModel = mVideos.get(position);



        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Video video = mVideos.get(position);
                    //play video using android api, when video view is clicked.
                    String url = Constant.video_URL+video.getVideoUrl(); // your URL here
                    Uri videoUri = Uri.parse(url);
                    holder.VideoTitle.setVideoURI(videoUri);
                    MediaController mediaController = new MediaController(mCtx);
                    mediaController.setAnchorView(holder.VideoTitle);
                    // Set video link (mp4 format )
                    //Uri videos = Uri.parse(videoUri);
                    holder.VideoTitle.setMediaController(mediaController);
                    holder.VideoTitle.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            holder.VideoTitle.start();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        VideoView VideoTitle;
        ImageButton imageButton;


        public ViewHolder(View itemView) {
            super(itemView);
            VideoTitle = itemView.findViewById(R.id.videoo);
            imageButton = itemView.findViewById(R.id.play);
        }

    }


}



