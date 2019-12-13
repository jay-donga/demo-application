package com.demoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoapplication.adapter.CustomPagerAdapter;
import com.demoapplication.model.Article;
import com.demoapplication.model.Content;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.List;

import static java.security.AccessController.getContext;

public class DetailActivity extends AppCompatActivity {

    List<Content> mList;
    TextView tvTitle,tvDescription,tvView,tvStar,tvList;
    ImageView ivGradient;
    int position = 0;
    Palette p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivGradient = findViewById(R.id.iv_gradiant);

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

        mList = (List<Content>) getIntent().getSerializableExtra("data");
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

        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onpageselect(int position){
        tvTitle.setText(mList.get(position).getTitle());
        tvDescription.setText(mList.get(position).getPromoMsg());
        tvView.setText(" "+mList.get(position).getViewCount()+" Reads");
        tvStar.setText(" "+mList.get(position).getLikeCount()+" Votes");
        tvList.setText(" "+mList.get(position).getReadCount()+" Parts");

        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Palette p = createPaletteSync(bitmap);

            Palette.Swatch ps = p.getDarkMutedSwatch();
            if(ps==null){
                ps=p.getDominantSwatch();
            }

            ivGradient.setBackgroundColor(ps.getRgb());
            setToolbarColor(ps);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        Picasso.get().load(mList.get(position).getCover().getThumbnail()).into(target);

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

    // Generate palette synchronously and return it
    public Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    public void setToolbarColor(Palette.Swatch vibrantSwatch) {
        // Generate the palette and get the vibrant swatch
        // See the createPaletteSync() method
        // from the code snippet above


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Load default colors
        int backgroundColor = ContextCompat.getColor(this,
                R.color.colorPrimary);
        int textColor = ContextCompat.getColor(this,
                R.color.color_gray);

        // Check that the Vibrant swatch is available
        if(vibrantSwatch != null){
            backgroundColor = vibrantSwatch.getRgb();
            textColor = vibrantSwatch.getTitleTextColor();
        }

        // Set the toolbar background and text colors
        toolbar.setBackgroundColor(backgroundColor);
        toolbar.setTitleTextColor(textColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(backgroundColor);
        }
    }



}
