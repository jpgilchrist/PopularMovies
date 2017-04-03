package com.jpgilchrist.android.popularmovies.tmdb;

import com.google.gson.Gson;

/**
 * Created by jpegz on 4/2/17.
 */

public enum GsonFactory {
    INSTANCE;

    private final Gson gson;

    GsonFactory() {
        this.gson = new Gson();
    }

    public Gson getGson() {
        return this.gson;
    }
}
