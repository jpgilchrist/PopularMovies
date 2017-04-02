package com.jpgilchrist.android.popularmovies.tmdb;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jpgilchrist.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jpegz on 3/22/17.
 */

public class TMDBUtils {

    private static final String TAG = TMDBUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String IMAGE_URL = "https://image.tmdb.org";
    private static final String IMAGE_URL_PATH_PREFIX = "t/p/";

    private static final String POPULAR_MOVIES_PATH = "3/movie/popular";
    private static final String TOPRATED_MOVIES_PATH = "3/movie/top_rated";

    private static final String QP_APIKEY_NAME = "api_key";
    private static final String QP_APIKEY_VALUE = BuildConfig.TMDB_API_KEY;

    private static final String QP_LANG_NAME = "language";
    private static final String QP_LANG_VALUE = "en-US";

    private static final String QP_PAGE_NAME = "page";

    public enum ImageSize {
        W92("w92"), W154("w154"), W185("w185"), W342("w342"), W500("w500"), W780("w780"), ORIGINAL("original");

        private String path;

        ImageSize(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public static ImageSize fromPath(String path) {
            for (ImageSize imageSize : ImageSize.values()) {
                if (imageSize.path == path) {
                    return imageSize;
                }
            }
            return null;
        }
    }

    public static URL buildPublicMoviesURL(int page) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .path(POPULAR_MOVIES_PATH)
                .appendQueryParameter(QP_APIKEY_NAME, QP_APIKEY_VALUE)
                .appendQueryParameter(QP_LANG_NAME, QP_LANG_VALUE)
                .appendQueryParameter(QP_PAGE_NAME, String.valueOf(page))
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL buildTopRatedMoviesURL(int page) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .path(TOPRATED_MOVIES_PATH)
                .appendQueryParameter(QP_APIKEY_NAME, QP_APIKEY_VALUE)
                .appendQueryParameter(QP_LANG_NAME, QP_LANG_VALUE)
                .appendQueryParameter(QP_PAGE_NAME, String.valueOf(page))
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Uri buildPosterPathUri(String posterPath, ImageSize size) {
        Uri uri = Uri.parse(IMAGE_URL).buildUpon()
                .path(IMAGE_URL_PATH_PREFIX + size.getPath() + posterPath)
                .build();
        return uri;
    }

    public static TMDBPage getResponseFromURL(URL url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        TMDBPage page = null;

        try {
            Log.d(TAG, "Fetching URL " + url.toString());

            response = client.newCall(request).execute();

            Gson gson = new Gson();
            page = gson.fromJson(response.body().string(), TMDBPage.class);
        } catch (IOException | JsonSyntaxException e) {
            if (response != null) {
                response.close();
            }
        }

        return page;
    }

}
