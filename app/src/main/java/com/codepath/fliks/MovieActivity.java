package com.codepath.fliks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.fliks.adapters.MovieArrayAdapter;
import com.codepath.fliks.models.Movie;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
ArrayList <Movie> movies;
MovieArrayAdapter movieAdapter;
ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        //toolbar =(Toolbar) findViewById(R.id.)
        lvItems=(ListView)findViewById(R.id.lvMovies);
        movies =new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this,movies);
        lvItems.setAdapter(movieAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(MovieActivity.this,DetailMovieActivity.class);
                Gson movieOjectJson = new Gson();
                String movieJson =movieOjectJson.toJson((Movie)adapterView.getItemAtPosition(i));
                intent.putExtra("movie",movieJson);
                startActivity(intent);

            }
        });


        String url=" https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray movieJSONResult= null;
                try {
                    movieJSONResult=response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJSONResult));
                    movieAdapter.notifyDataSetChanged();
                   // Log.d("DEBUG",movies.toString());

                }
                catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
