package com.example.quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public  final String key="Kj27UryY4IWKnoJv6WYQtHPgvWofNn6PfCvFn2Ao";
    String category;
    private  RequestQueue requestQueue;
    CardView cardView;


    public TextView cardText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        FloatingActionButton fb=findViewById(R.id.share);
        TextView next=findViewById(R.id.next);
        TextView previous=findViewById(R.id.previous);
        cardView=findViewById(R.id.cardview);
        cardText=findViewById(R.id.cardText);
       Spinner spinner=findViewById(R.id.spinner);

       ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        category = spinner.getItemAtPosition(0).toString();
        fetch(category);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(category);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentQuote=cardText.getText().toString();
                if (!currentQuote.isEmpty()) {
                    shareQuote(currentQuote);
                } else {
                    Toast.makeText(MainActivity.this, "Select text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel any ongoing requests when the activity is destroyed
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
    private void fetch(String category){
         String url = "https://api.api-ninjas.com/v1/quotes?category="+category;

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String quote="";
                try {
                    JSONObject quote1=response.getJSONObject(0);
                     quote=quote1.getString("quote");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                cardText.setText(quote);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            public Map<String,String> getHeaders(){
                Map<String,String> headers=new HashMap<>();
                headers.put("X-Api-Key",key);
                return headers;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
       // category=adapterView.getItemAtPosition(0).toString();
        //fetch(category);
    }
    private void shareQuote(String quote) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote);

        startActivity(Intent.createChooser(shareIntent, "Share Quote via"));
    }

}