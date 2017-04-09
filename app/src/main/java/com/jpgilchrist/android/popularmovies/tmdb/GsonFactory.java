package com.jpgilchrist.android.popularmovies.tmdb;

import com.google.gson.Gson;

// enum singleton pattern to get a gson instance
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
