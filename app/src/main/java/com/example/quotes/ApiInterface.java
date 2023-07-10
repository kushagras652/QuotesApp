package com.example.quotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v1/quotes?category=happiness")
    Call<List<userModel>> getUser();
}
