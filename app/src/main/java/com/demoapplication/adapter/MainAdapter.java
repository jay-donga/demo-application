package com.demoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapplication.DetailActivity;
import com.demoapplication.R;
import com.demoapplication.model.Example;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    List<Example> mList = new ArrayList<>();
    Context mContext;

    public MainAdapter(List<Example> mList,Context ctx) {
        this.mList = mList;
        this.mContext = ctx;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerview;
        TextView tvTitle, tvDescription;
        RelativeLayout rlTop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerview_gallery);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            rlTop = itemView.findViewById(R.id.rl_top);
        }
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_main, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
            holder.tvTitle.setText(mList.get(position).getTitle());
            holder.tvDescription.setText(mList.get(position).getDescription());
            GalleryAdapter adapter  = new GalleryAdapter(mList.get(position).getArticles(),mContext);
        //use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.recyclerview.setLayoutManager(layoutManager);
        holder.recyclerview.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
