package com.codepath.flickster.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.activity.MovieDetailActivity;
import com.codepath.flickster.activity.TrailerActivity;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.id.buttonPlay;
import static com.codepath.flickster.R.id.ivMovieImage;
import static com.codepath.flickster.R.id.tvOverview;
import static com.codepath.flickster.R.id.tvTitle;

/**
 * Created by praniti on 9/10/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public enum MovieType {
        POPULAR, NONPOPULAR
    }

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView poster;
        Button buttonPlay;
        ImageView ivMovieImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    // Get a View that displays the data at the specified position in the data set.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for position
        final Movie movie = getItem(position);

        int type = getItemViewType(position);
        int orientation = getContext().getResources().getConfiguration().orientation;

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row

            convertView = getInflatedLayoutForType(type, orientation, parent);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(tvOverview);
            viewHolder.buttonPlay = (Button) convertView.findViewById(buttonPlay);
            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(ivMovieImage);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.poster = (ImageView) convertView.findViewById(ivMovieImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.poster = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            }

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //populate data
        if (viewHolder.title != null) {
            viewHolder.title.setText(movie.getOriginalTitle());
        }

        if (viewHolder.overview != null) {
            viewHolder.overview.setText(movie.getOverview());
        }

        if (viewHolder.poster != null) {
            viewHolder.poster.setImageResource(0);

            if (orientation == Configuration.ORIENTATION_PORTRAIT && type == MovieType.NONPOPULAR.ordinal()) {
                Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop().placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(15, 15)).into(viewHolder.poster);

            } else if (orientation == Configuration.ORIENTATION_PORTRAIT && type == MovieType.POPULAR.ordinal()) {
                Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerCrop().placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(15, 15)).into(viewHolder.poster);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getBackdropPath()).resize(1600, 800).centerCrop().placeholder(R.drawable
                    .placeholder).transform(new RoundedCornersTransformation(15, 15)).into(viewHolder.poster);
            }
        }

        if (viewHolder.buttonPlay != null) {
            viewHolder.buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TrailerActivity.class);
                    intent.putExtra("movieId", movie.getId());
                    view.getContext().startActivity(intent);
                }
            });

        }

        if (viewHolder.ivMovieImage != null) {
            viewHolder.ivMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Double movieRate = movie.getVoteAverage() / 2;

                    Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                    intent.putExtra("movieTitle", movie.getOriginalTitle());
                    intent.putExtra("movieOverview", movie.getOverview());
                    intent.putExtra("movieRate", movieRate);
                    intent.putExtra("moviePoster", movie.getPosterPath());
                    view.getContext().startActivity(intent);
                }
            });

        }

        return convertView;
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        return MovieType.values().length;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        if (movie.getVoteAverage() > 5)
            return MovieType.POPULAR.ordinal();
        else
            return MovieType.NONPOPULAR.ordinal();
    }

    // Given the item type, responsible for returning the correct inflated XML layout file
    private View getInflatedLayoutForType(int type, int orientation, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (type == MovieType.POPULAR.ordinal() && orientation == Configuration.ORIENTATION_PORTRAIT) {
            return inflater.inflate(R.layout.item_popular_movie, parent, false);
        } else if (type == MovieType.NONPOPULAR.ordinal() && orientation == Configuration.ORIENTATION_PORTRAIT) {
            return inflater.inflate(R.layout.item_movie, parent, false);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return inflater.inflate(R.layout.item_movie, parent, false);
        } else {
            return null;
        }
    }

}
