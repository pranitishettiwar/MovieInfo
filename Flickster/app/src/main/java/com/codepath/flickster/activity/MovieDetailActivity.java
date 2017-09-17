package com.codepath.flickster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.codepath.flickster.R;

/**
 * Created by praniti on 9/16/17.
 */

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
