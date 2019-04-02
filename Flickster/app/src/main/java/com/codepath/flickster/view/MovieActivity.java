package com.codepath.flickster.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.flickster.R;
import com.codepath.flickster.adapter.MovieArrayAdapter;
import com.codepath.flickster.contract.MovieContract;
import com.codepath.flickster.model.Movie;
import com.codepath.flickster.presenter.MoviePresenter;

public class MovieActivity extends Activity implements MovieContract.View {

    private MovieContract.Presenter mPresenter;
    private ListView lvItems;
    MovieArrayAdapter movieAdapter;

    @Override
    public void init() {
        lvItems = (ListView) findViewById(R.id.lvMovies);
        mPresenter.loadMovies();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadDataInList(List<Movie> movies) {
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        movieAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mPresenter = new MoviePresenter(this);
        mPresenter.start();
    }
}