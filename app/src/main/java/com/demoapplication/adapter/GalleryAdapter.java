package com.demoapplication.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    public List<Article> mList = new ArrayList<>();
    Context mContext;

    public GalleryAdapter(List<Article> mList,Context mContext) {
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

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(mList.get(position).getImage()).into(holder.ivImage);
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvDescription.setText(mList.get(position).getDescription());
        holder.tvView.setText(mList.get(position).getViews());
        holder.tvStar.setText(mList.get(position).getStars());
        holder.tvList.setText(mList.get(position).getList());



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
