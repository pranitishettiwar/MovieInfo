package com.codepath.flickster.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    public String getPosterPath() {
        return  posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Integer getId() {
        return id;
    }

    private String posterPath;
    private String originalTitle;
    private Integer id;

    private Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.optString("image");
        this.originalTitle = jsonObject.getString("title");
        this.id = jsonObject.getInt("id");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}