package com.codepath.flickster.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by praniti on 9/17/17.
 */

public class Trailer {

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    String id;
    String key;

    public Trailer(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.key = jsonObject.getString("key");
    }

    public static ArrayList<Trailer> fromJSONArray(JSONArray array) {
        ArrayList<Trailer> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Trailer(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
