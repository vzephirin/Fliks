package com.codepath.fliks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.fliks.R;
import com.codepath.fliks.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by brh on 2/9/2018.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    class ViewHolder {
        @BindView(R.id.movie_item_img_cover)
        ImageView img_cover;
        @BindView(R.id.movie_item_img_play)
        ImageView img_play;
        @BindView(R.id.movie_item_tv_title)
        TextView tv_tile;
        @BindView(R.id.movie_item_tv_desc)
        TextView tv_desc;
        @BindView(R.id.movie_item_ll_desc)
        LinearLayout ll_desc;
        @BindView(R.id.movie_item_ll_cover)
        RelativeLayout rl_cover;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    public MovieArrayAdapter (Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1,movies);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie =getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(getContext()).downloader(new OkHttp3Downloader(client)).build();*/
        String imgUri = getBaseUrl();

        if (movie.getVoteAverage() > 5) {

            int orientation = getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.ll_desc.setVisibility(View.GONE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.ll_desc.setVisibility(View.VISIBLE);
            }

            viewHolder.img_play.setVisibility(View.VISIBLE);

            imgUri += "w1280/" + movie.getBackdropPath();

        } else {
            viewHolder.ll_desc.setVisibility(View.VISIBLE);
            viewHolder.img_play.setVisibility(View.GONE);
            imgUri += "w500/" + movie.getPosterPath();
        }

        Picasso.with(getContext())
                .load(imgUri)
                .placeholder(R.drawable.play)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.img_cover, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
        viewHolder.tv_tile.setText(movie.getOriginalTitle());
        viewHolder.tv_desc.setText(String.format(Locale.US, "%s ...", movie.getOverview().substring(0, movie.getOverview().length() > 100 ? 100 : movie.getOverview().length())));

        Log.d("DEBUG",imgUri);
        return convertView;
    }






        private String getBaseUrl(){
        return "https://image.tmdb.org/t/p/";
        }


       // return super.getView(position, convertView, parent);

}
