package com.demoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapplication.DetailActivity;
import com.demoapplication.R;
import com.demoapplication.model.Article;
import com.demoapplication.model.Content;
import com.demoapplication.model.Cover;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    public List<Content> mList = new ArrayList<>();
    Context mContext;

    public GalleryAdapter(List<Content> mList,Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerview;
        TextView tvTitle, tvDescription,tvView, tvStar, tvList;
        ImageView ivImage;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerview_gallery);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivImage = itemView.findViewById(R.id.iv_article);
            tvView = itemView.findViewById(R.id.tv_views);
            tvStar = itemView.findViewById(R.id.tv_stars);
            tvList = itemView.findViewById(R.id.tv_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), DetailActivity.class);
                    i.putExtra("data", (Serializable) mList);
                    i.putExtra("position", getAdapterPosition());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }

    @NonNull
    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gallery, parent, false);

        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.90);
        v.setLayoutParams(layoutParams);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.MyViewHolder holder, int position) {

        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvDescription.setText(mList.get(position).getPromoMsg());
        holder.tvView.setText(mList.get(position).getViewCount()+"");
        holder.tvStar.setText(mList.get(position).getLikeCount()+"");
        holder.tvList.setText(mList.get(position).getReadCount()+"");


        try{

           //Picasso.get().load(mList.get(position).getCover().getThumbnail()).into(holder.ivImage);
            Picasso.get()
                    .load("https://cdn.storymirror.com/"+mList.get(position).getCover().getThumbnail())
                    .into(holder.ivImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Picasso.get().load(mList.get(position).getCover().getThumbnail()).into(holder.ivImage);
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
