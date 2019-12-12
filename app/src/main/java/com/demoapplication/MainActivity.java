package com.demoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.demoapplication.adapter.MainAdapter;
import com.demoapplication.model.Example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);

        try {
            mList= (ArrayList<Example>) fromJson(AssetJSONFile(MainActivity.this),
                    new TypeToken<ArrayList<Example>>() {
                    }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }


        adapter = new MainAdapter(mList,this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);


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
