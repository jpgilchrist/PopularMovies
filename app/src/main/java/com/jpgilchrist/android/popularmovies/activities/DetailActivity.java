package com.jpgilchrist.android.popularmovies.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.tmdb.GsonFactory;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private TMDBPage.TMDBResult result;

    private TextView titleTextView;
    private TextView yearTextView;
    private TextView ratingTextiew;
    private TextView synopsisTextView;
    private ImageView imageView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get all the view objects
        titleTextView = (TextView) findViewById(R.id.MOVIE_DETAIL_TITLE);
        yearTextView = (TextView) findViewById(R.id.MOVIE_DETAIL_YEAR);
        ratingTextiew = (TextView) findViewById(R.id.MOVIE_DETAIL_RATING);
        synopsisTextView = (TextView) findViewById(R.id.MOVIE_DETAIL_SYNOPSIS);
        imageView = (ImageView) findViewById(R.id.MOVIE_DETAIL_IMAGE);

        Intent starter = getIntent(); // get the intent that started the activity
        if (starter != null) {
            Bundle extras = starter.getExtras();
            String jsonifiedTMBDResult = extras.getString(Intent.EXTRA_TEXT); // pull the stringified json data from the bundle
            if (jsonifiedTMBDResult != null && !jsonifiedTMBDResult.isEmpty()) {
                try {
                    // parse the stringified object and build a TMDBPage obejct
                    this.result = GsonFactory.INSTANCE.getGson().fromJson(jsonifiedTMBDResult, TMDBPage.TMDBResult.class);
                } catch (JsonSyntaxException e) {
                    this.result = null;
                }
            }
        }

        initializeContent();

        // setup the action bar to have the leftward facing back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializeContent() {

        // setup the views to display the correct data
        titleTextView.setText(result.getTitle());
        synopsisTextView.setText(result.getOverview());
        ratingTextiew.setText(String.valueOf(result.getVote_average()));

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        String releaseDate = result.getRelease_date();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            try {
                Date date = dateFormat.parse(releaseDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                yearTextView.setText("(" + calendar.get(Calendar.YEAR) + ")");
            } catch (ParseException e) {
                Log.d(TAG, "The date returned by the API isn't parsable with the established format.");
            }
        }

        // use picasso to load the poster image
        Picasso.with(this)
                .load(TMDBUtils.buildPosterPathUri(result.getPoster_path(), TMDBUtils.ImageSize.W342))
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the detail menu as the menu for this activity
        new MenuInflater(this).inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            // navigate to parent activity (main)
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            // use default bahavior
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
