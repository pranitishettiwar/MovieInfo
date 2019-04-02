package com.codepath.flickster.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flickster.R;
import com.codepath.flickster.contract.MovieDetailContract;
import com.codepath.flickster.presenter.MovieDetailPresenter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MovieDetailActivity extends Activity implements MovieDetailContract.View {

    private MovieDetailContract.Presenter mPresenter;
    TextView title, index;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = (TextView) findViewById(R.id.tvMovieDetailTitle);
        poster = (ImageView) findViewById(R.id.ivMovieDetailPoster);
        index = (TextView) findViewById(R.id.tvMovieDetailIndex);

        final int movieId = getIntent().getIntExtra("id", 0);

        mPresenter = new MovieDetailPresenter(this, movieId);
        mPresenter.start();
    }

    @Override
    public void init() {
        mPresenter.getMovieDetails();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadMovieDetails(JSONObject movieDetail) {
        String movieTitle = movieDetail.optString("title");
        title.setText(movieTitle);
        index.setText(movieDetail.optString("index"));
        if (poster != null) {
            poster.setImageResource(0);
            Picasso.with(getApplicationContext()).load(movieDetail.optString("image")).resize(1000, 1200).into(poster);
        }
    }



        /*String url = "https://us-central1-modern-venture-600.cloudfunctions.net/api/movies/" + movieId;

        if (Cache.getInstance().get(movieId) != null) {
            Log.d("DEBUG", "Populating Movie details from cache");
            populateMovieDetails(Cache.getInstance().get(movieId));
        } else {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    try {
                        JSONObject movieDetail = new JSONObject(responseString);
                        populateMovieDetails(movieDetail);

                        Log.d("DEBUG", "Populating Movie details from API endpoint");

                        Cache.getInstance().put(movieId, movieDetail);
                    } catch (JSONException e) {
                        Log.d("DEBUG", "JSON Exception" + e);
                    }
                }
            });
        }*/

}