package com.codepath.flickster.contract;

import org.json.JSONObject;

public interface MovieDetailContract {

    interface View {

        void init();

        void showError(String message);

        void loadMovieDetails(JSONObject movieDetail);
    }

    interface Presenter {

        void start();

        void getMovieDetails();

    }
}
