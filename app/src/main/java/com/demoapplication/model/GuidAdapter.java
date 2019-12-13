package com.demoapplication.model;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GuidAdapter extends TypeAdapter<Cover> {

    private final Gson gson;

    public GuidAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter jsonWriter, Cover guid) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Cover read(com.google.gson.stream.JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BEGIN_OBJECT:
                // full object, forward to Gson
                return gson.fromJson(jsonReader, Cover.class);
            case STRING:
                // only a String, create the object
                return new Cover(jsonReader.nextString(), "","","");

            default:
                throw new RuntimeException("Expected object or string, not " + jsonReader.peek());
        }
    }


}