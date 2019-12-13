package com.demoapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.demoapplication.adapter.MainAdapter;
import com.demoapplication.model.Block;
import com.demoapplication.model.Data;
import com.demoapplication.model.Example;
import com.demoapplication.model.MainData;
import com.demoapplication.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    List<Example> mList = new ArrayList<Example>();
    MainAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        //use a linear layout manager
        layoutManager = new LinearLayoutManager(this);

        getStoryList();

        /*adapter = new MainAdapter(mList,this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);*/

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void getStoryList() {

        try {

            mList= (ArrayList<Example>) fromJson(AssetJSONFile(MainActivity.this),
                    new TypeToken<ArrayList<Example>>() {
                    }.getType());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectNonSdkApiUsage()
                    .penaltyLog()
                    .build());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://devapi.storymirror.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiinterface = retrofit.create(ApiInterface.class);
            Call<MainData> stories = apiinterface.getStories();
            stories.enqueue(new Callback<MainData>() {
                @Override
                public void onResponse(Call<MainData> call, Response<MainData> response) {
                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                    Log.e("my log","result is : success");
                    MainData md = (MainData) response.body();

                    List<Block> mList = new ArrayList<>();
                    for(Block bloack:md.getBlocks()){
                        if(bloack.getTitle().equalsIgnoreCase("All Genres")||bloack.getTitle().equalsIgnoreCase("Contests")){
                            continue;
                        }else{
                            mList.add(bloack);
                        }
                    }
                    adapter = new MainAdapter(mList,MainActivity.this);
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.setAdapter(adapter);

                  }

                @Override
                public void onFailure(Call<MainData> call, Throwable t) {
                    Log.e("my log","result is : failure : "+t.toString());
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_LONG).show();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public String AssetJSONFile (Context context) throws IOException {
        AssetManager manager = getAssets();
        InputStream file = manager.open("example.json");
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

}
