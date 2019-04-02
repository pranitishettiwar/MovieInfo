package com.codepath.flickster.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.view.MovieDetailActivity;
import com.codepath.flickster.model.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.id.ivMovieImage;
import static com.codepath.flickster.R.id.tvOverview;
import static com.codepath.flickster.R.id.tvTitle;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView poster;
        ImageView ivMovieImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = getItem(position);

        int type = getItemViewType(position);
        int orientation = getContext().getResources().getConfiguration().orientation;

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = getInflatedLayoutForType(type, orientation, parent);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(tvOverview);
            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(ivMovieImage);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.poster = (ImageView) convertView.findViewById(ivMovieImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.poster = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.title != null) {
            viewHolder.title.setText(movie.getOriginalTitle());
        }

        if (viewHolder.poster != null) {
            viewHolder.poster.setImageResource(0);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop().placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(15, 15)).into(viewHolder.poster);

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getPosterPath()).resize(1200, 800).centerCrop().placeholder(R.drawable
                    .placeholder).transform(new RoundedCornersTransformation(15, 15)).into(viewHolder.poster);

            }
        }

        if (viewHolder.ivMovieImage != null) {
            viewHolder.ivMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntentToMovieDetail(view, movie);
                }
            });

        }

        if (viewHolder.poster != null) {
            viewHolder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntentToMovieDetail(view, movie);

                }
            });

        }

        return convertView;
    }

    private void sendIntentToMovieDetail(View view, Movie movie) {
        Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movie.getId());
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private View getInflatedLayoutForType(int type, int orientation, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return inflater.inflate(R.layout.item_movie, parent, false);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return inflater.inflate(R.layout.item_movie, parent, false);
        } else {
            return null;
        }
    }

}