package com.codepath.flickster.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by praniti on 9/17/17.
 */

public class TrailerActivity extends YouTubeBaseActivity {

    private static final String KEY = "AIzaSyA2YDEo-TXLEWLNJbkFvGFWg2OzmmDGe6E";
    ArrayList<Trailer> trailers;
    String trailerKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        Integer movieId = getIntent().getIntExtra("movieId", 0);

        trailers = new ArrayList<>();
        getTrailerKey(movieId);

    }

    private void loadYoutubeVideo() {
        final YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                // do any work here to cue video, play video, etc.
                youTubePlayer.loadVideo(trailerKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
                youTubeInitializationResult) {

            }
        });

    }

    public void getTrailerKey(int movieId) {

        StringBuilder str = new StringBuilder(0);
        str.append("https://api.themoviedb.org/3/movie/");
        str.append(movieId);
        str.append("/videos?");
        str.append("api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed");
        String url = str.toString();

        Log.d("DEBUG : Trailer URL", url);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray trailerJsonResults = null;

                try {
                    trailerJsonResults = response.getJSONArray("results");
                    trailers.addAll(Trailer.fromJSONArray(trailerJsonResults));
                    trailerKey = trailers.get(0).getKey();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadYoutubeVideo();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
