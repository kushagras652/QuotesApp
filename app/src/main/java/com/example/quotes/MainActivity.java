package com.example.quotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String api="https://api-ninjas.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        FloatingActionButton fb=findViewById(R.id.share);
        TextView next=findViewById(R.id.next);
        TextView previous=findViewById(R.id.previous);

        RetrofitInstance.getInstance().apiInterface.getUser()
                .enqueue(new Callback<List<userModel>>() {
            @Override
            public void onResponse(Call<List<userModel>> call, Response<List<userModel>> response) {
                Log.d("god","onSuccess" + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<userModel>> call, Throwable t) {
                Log.d("nidhish","onFailure" + t.getLocalizedMessage());
            }
        });
    }
}