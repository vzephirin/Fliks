package com.codepath.fliks.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by brh on 2/9/2018.
 */

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("original_title")
    private String originalTitle;

    private String overview;

    @SerializedName("vote_count")
    private int voteCount;

    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;
    private double popularity;
    private boolean adult;
    @SerializedName("original_language")
    private String originalLanguage;

    public Long getId() {
        return id;
    }

    private Long id;




    public String getPosterPath() {
        return this.posterPath;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Movie(JSONObject jsonObject)throws JSONException{
        this.posterPath=jsonObject.getString("poster_path");
        this.originalTitle=jsonObject.getString("original_title");
        this.overview=jsonObject.getString("overview");

        this.backdropPath=jsonObject.getString("backdrop_path");
        this.video=jsonObject.getBoolean("video");
        this.voteCount=jsonObject.getInt("vote_count");

        this.popularity=jsonObject.getDouble("popularity");
        this.adult=jsonObject.getBoolean("adult");
        this.originalLanguage=jsonObject.getString("original_language");
        this.voteAverage=jsonObject.getDouble("vote_average");
        this.id=jsonObject.getLong("id");
        this.releaseDate=jsonObject.getString("release_date");

    }
    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> movies =new ArrayList<>();
        for (int i=0; i<array.length();i++){
            try {
                movies.add(new Movie(array.getJSONObject(i)));
                //Log.d("DEBUG",array.getJSONArray(i).toString());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        return movies;
    }
}
