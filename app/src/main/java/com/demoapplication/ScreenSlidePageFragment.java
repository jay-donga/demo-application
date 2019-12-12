package com.demoapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demoapplication.model.Article;
import com.squareup.picasso.Picasso;

public class ScreenSlidePageFragment extends Fragment {

    Article article;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            article = (Article) bundle.getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.pager_item, container, false);

        ImageView imageView =  rootView.findViewById(R.id.imageView);
        Picasso.get().load(article.getImage()).into(imageView);
        Log.e("my log","iamge : "+article.getImage());

        return rootView;
    }
}