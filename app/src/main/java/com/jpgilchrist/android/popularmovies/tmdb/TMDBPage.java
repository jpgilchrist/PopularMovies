package com.jpgilchrist.android.popularmovies.tmdb;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jpegz on 3/27/17.
 */

public class TMDBPage {

    public static final int ITEMS_PER_PAGE = 20; // from TMDB api docs

    private int page;
    private int total_results;
    private int total_pages;
    private List<TMDBResult> results;

    public TMDBPage () {
        this.page = 1;
        this.total_pages = 0;
        this.total_results = 0;
        this.results = new ArrayList<TMDBResult>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<TMDBResult> getResults() {
        return results;
    }

    public void setResults(List<TMDBResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "TMDBPage{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }

    public class TMDBResult {
        private String poster_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private int[] genre_ids;
        private Number id;
        private String original_title;
        private String original_language;
        private String title;
        private String backdrop_path;
        private Number popularity;
        private int vote_count;
        private boolean video;
        private Number vote_average;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int[] getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(int[] genre_ids) {
            this.genre_ids = genre_ids;
        }

        public Number getId() {
            return id;
        }

        public void setId(Number id) {
            this.id = id;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public Number getPopularity() {
            return popularity;
        }

        public void setPopularity(Number popularity) {
            this.popularity = popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public Number getVote_average() {
            return vote_average;
        }

        public void setVote_average(Number vote_average) {
            this.vote_average = vote_average;
        }

        @Override
        public String toString() {
            return "TMDBResult{" +
                    "poster_path='" + poster_path + '\'' +
                    ", adult=" + adult +
                    ", overview='" + overview + '\'' +
                    ", release_date=" + release_date +
                    ", genre_ids=" + Arrays.toString(genre_ids) +
                    ", id=" + id +
                    ", original_title='" + original_title + '\'' +
                    ", original_language='" + original_language + '\'' +
                    ", title='" + title + '\'' +
                    ", backdrop_path='" + backdrop_path + '\'' +
                    ", popularity=" + popularity +
                    ", vote_count=" + vote_count +
                    ", video=" + video +
                    ", vote_average=" + vote_average +
                    '}';
        }
    }
}
