package com.codepath.flickster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.squareup.picasso.Picasso;

import static com.codepath.flickster.R.id.ivMovieDetailPoster;
import static com.codepath.flickster.R.id.rateMovieDetail;
import static com.codepath.flickster.R.id.tvMovieDetailOverview;
import static com.codepath.flickster.R.id.tvMovieDetailTitle;

/**
 * Created by praniti on 9/16/17.
 */

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView title = (TextView) findViewById(tvMovieDetailTitle);
        TextView overview = (TextView) findViewById(tvMovieDetailOverview);
        ImageView poster = (ImageView) findViewById(ivMovieDetailPoster);
        RatingBar rate = (RatingBar) findViewById(rateMovieDetail);

        String movieTitle = getIntent().getStringExtra("movieTitle");
        String movieOverview = getIntent().getStringExtra("movieOverview");
        String moviePoster = getIntent().getStringExtra("moviePoster");
        Double movieRate = getIntent().getDoubleExtra("movieRate", 0);

        title.setText(movieTitle);
        overview.setText(movieOverview);
        rate.setNumStars(5);
        rate.setRating(movieRate.floatValue());

        if (poster != null) {
            poster.setImageResource(0);

            Picasso.with(this).load(moviePoster).resize(1000, 1200).into(poster);

        }
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
