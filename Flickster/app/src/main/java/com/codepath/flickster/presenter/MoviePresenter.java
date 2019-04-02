package com.codepath.flickster.presenter;

import java.util.ArrayList;

import android.util.Log;

import com.codepath.flickster.contract.MovieContract;
import com.codepath.flickster.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.View mView;
    private ArrayList<Movie> movies;
    private static final String BASE_URL = "https://us-central1-modern-venture-600.cloudfunctions.net/api/movies";

    public MoviePresenter(MovieContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void start() {
        mView.init();
        movies = new ArrayList<>();
    }

    @Override
    public void loadMovies() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mView.showError("Error Occured");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONArray movieJsonResults = new JSONArray(responseString);
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    mView.loadDataInList(movies);
                } catch (JSONException e) {
                    Log.d("DEBUG", "JSON Exception" + e);
                }
            }
        });

    }
}
