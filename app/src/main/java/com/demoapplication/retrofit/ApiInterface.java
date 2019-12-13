package com.demoapplication.retrofit;

import com.demoapplication.model.Example;
import com.demoapplication.model.MainData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("1.0/story/trending/page/1?language=english")
    Call<MainData> getStories();

}
