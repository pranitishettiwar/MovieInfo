package com.codepath.flickster.contract;

import java.util.List;

import com.codepath.flickster.model.Movie;

public interface MovieContract {

    interface View {
        void init();

        void showError(String message);

        void loadDataInList(List<Movie> movies);
    }

    interface Presenter {

        void start();

        void loadMovies();

    }
}
