package com.demoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.demoapplication.adapter.CustomPagerAdapter;
import com.demoapplication.model.Article;

import java.io.Serializable;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    List<Article> mList;
    TextView tvTitle,tvDescription,tvView,tvStar,tvList;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);

        tvView = findViewById(R.id.tv_views);
        tvStar = findViewById(R.id.tv_stars);
        tvList = findViewById(R.id.tv_list);
        ViewPager mViewPager = findViewById(R.id.pager);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mList = (List<Article>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("position",0);
        onpageselect(position);

        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        mViewPager.setPageMargin((int) (-width*0.4));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutTransformation());
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              onpageselect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void onpageselect(int position){
        tvTitle.setText(mList.get(position).getTitle());
        tvDescription.setText(mList.get(position).getDescription());
        tvView.setText(mList.get(position).getViews());
        tvStar.setText(mList.get(position).getStars());
        tvList.setText(mList.get(position).getList());
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();

            bundle.putSerializable("data", (Serializable) mList.get(position));
            Fragment f = new ScreenSlidePageFragment();
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    public class ZoomOutTransformation implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.65f;
        private static final float MIN_ALPHA = 1f;

        @Override
        public void transformPage(View page, float position) {

            if (position <-1){  // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            }

            else if (position <=1){ // [-1,1]

                page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
                page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
                page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));

            }
            else {  // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }


        }
    }

}
