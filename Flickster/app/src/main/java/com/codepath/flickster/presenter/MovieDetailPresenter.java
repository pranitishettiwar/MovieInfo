package com.codepath.flickster.presenter;

import android.util.Log;

import com.codepath.flickster.contract.MovieDetailContract;
import com.codepath.flickster.util.Cache;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;
    private int mMovieId;
    private static final String BASE_URL = "https://us-central1-modern-venture-600.cloudfunctions.net/api/movies/";

    public MovieDetailPresenter(MovieDetailContract.View mView, int movieId) {
        this.mMovieId = movieId;
        this.mView = mView;
    }

    @Override

    public void start() {
        mView.init();
    }

    @Override
    public void getMovieDetails() {
        String url = BASE_URL + mMovieId;

        if (Cache.getInstance().get(mMovieId) != null) {
            Log.d("DEBUG", "Populating Movie details from cache");
            mView.loadMovieDetails(Cache.getInstance().get(mMovieId));

        } else {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    mView.showError("Error Occured");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    try {
                        JSONObject movieDetail = new JSONObject(responseString);
                        mView.loadMovieDetails(movieDetail);

                        Log.d("DEBUG", "Populating Movie details from API endpoint");

                        Cache.getInstance().put(mMovieId, movieDetail);
                    } catch (JSONException e) {
                        Log.d("DEBUG", "JSON Exception" + e);
                    }
                }
            });
        }

    }
}
