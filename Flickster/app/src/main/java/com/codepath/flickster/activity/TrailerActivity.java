package com.codepath.flickster.activity;

import android.os.Bundle;

import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by praniti on 9/17/17.
 */

public class TrailerActivity extends YouTubeBaseActivity {

    private static final String KEY = "AIzaSyA2YDEo-TXLEWLNJbkFvGFWg2OzmmDGe6E";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        YouTubePlayerView youTubePlayerView =
            (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(KEY,
            new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {

                    // do any work here to cue video, play video, etc.
                    youTubePlayer.loadVideo("5xVh-7ywKpE");
                }
                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
    }
}
