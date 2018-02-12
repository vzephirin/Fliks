package com.codepath.fliks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.fliks.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2/11/2018.
 */

public class DetailMovieActivity extends AppCompatActivity {
    YouTubePlayerSupportFragment youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        Movie movieitem= null;
        try {
            JSONObject movieJsonobject = new JSONObject(getIntent().getStringExtra("movie"));
            movieitem = new Movie (movieJsonobject);
            Log.d("DEBUG : ",getIntent().getStringExtra("movie"));

        } catch (JSONException e) {

            e.printStackTrace();
        }
        if (movieitem!=null){
            TextView tvTitle=(TextView) findViewById(R.id.tvTitled);
            TextView tvOverview = (TextView) findViewById(R.id.tvOverviewd);
           //  ImageView ivImage=(ImageView) findViewById(R.id.ivMovieChanged);

            //ivImage.setImageResource(0);
            youTubePlayerView =
                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fgVideoDetailMovie);

            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBard);
            TextView popularity = (TextView) findViewById(R.id.tvPopularityd);
            popularity.setText(String.format(Locale.US, "POPULARITY : %s", Double.toString(movieitem.getPopularity())));
            TextView dateRelease = (TextView) findViewById(R.id.tvDateReleased);
            popularity.setText(" date Release : "+movieitem.getReleaseDate());
            ratingBar.setRating((float)movieitem.getVoteAverage()/2);

            tvTitle.setText(movieitem.getOriginalTitle());
            tvOverview.setText(movieitem.getOverview());
/*
            int orientation= getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(this).load(movieitem.getPosterPath()).transform(new RoundedTransformation(10,10)).into(ivImage);
                //code for portrait mode
            } else {
                Picasso.with(this).load(movieitem.getBackdropPath()).transform(new RoundedTransformation(10,10)).into(ivImage);
                //code for landscape mode
            }
            */
            loadVideo(movieitem.getId());
        }



        }
    private void loadVideo(long id) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    final JSONArray jsonArray = jsonObject.getJSONArray("results");

                    final String videoKey = jsonArray.getJSONObject(0).getString("key");

                    DetailMovieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                            youTubePlayerView.initialize("AIzaSyBwWhROK0sjqN1H49UX0etqKdtHhdBdoJA",
                                    new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                            YouTubePlayer youTubePlayer, boolean b) {

                                            // do any work here to cue video, play video, etc.

                                            youTubePlayer.cueVideo(videoKey);

                                        }
                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                            YouTubeInitializationResult youTubeInitializationResult) {

                                        }
                                    });

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

}
