package com.jpgilchrist.android.popularmovies.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jpgilchrist.android.popularmovies.adapters.MovieGridAdapter;
import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.tmdb.GsonFactory;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<TMDBPage>,
        MovieGridAdapter.OnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MovieGridAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static int TMDB_LOADER_ID = 9001; //unique loader id
    private static boolean PREFERENCES_HAVE_CHANGED = false; // used to check whether preferences have changed
    private static TMDBUtils.Sort CURRENT_SORT_METHOD = TMDBUtils.Sort.POPULAR; // cache the current SORT method for use by AsyncTaskLoader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new GridLayoutManager(this, 2); // grid layout manager 2 wide

        // custom grid adapter initialized with an onClickHandler
        adapter = new MovieGridAdapter(MainActivity.this /* onClickHandler */);

        // setup the recycler view with layout manager and movie grid adapter
        recyclerView = (RecyclerView) findViewById(R.id.movie_grid_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // start listening for preference changes
        initializeSharedPreferencesListener();

        // initialize the AsyncTaskLoader
        getSupportLoaderManager().initLoader(TMDB_LOADER_ID, null, MainActivity.this);
    }

    /*
     * sets up listeners for sharedPreferences change events
     */
    private void initializeSharedPreferencesListener() {
        // get the shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * if the shared preferences contains the key then we need to initialize the AsyncTaskLoader
         * with the correct with the TMBDUtils.SORT method
         */
        if (sharedPreferences.contains(getString(R.string.SORT_PREF_KEY))) {
            String sortValue = sharedPreferences.getString(
                    getString(R.string.SORT_PREF_KEY), getString(R.string.SORT_PREF_DEFAULT_VALUE));
            CURRENT_SORT_METHOD = TMDBUtils.Sort.fromSortValue(sortValue);
        }

        // actually start listening to preference change events
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // if preferences have changed then grab the SORT method and then restart the loader
        if (PREFERENCES_HAVE_CHANGED) {
            PREFERENCES_HAVE_CHANGED = false;

            readSharedPreferencesForSortOrder();
            getSupportLoaderManager().restartLoader(TMDB_LOADER_ID, null, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister the shared preferences change listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /*
     * convenience method to pull the SORT method out of the shared preferences
     */
    private void readSharedPreferencesForSortOrder() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        CURRENT_SORT_METHOD = TMDBUtils.Sort.fromSortValue(
                sharedPreferences.getString(getString(R.string.SORT_PREF_KEY), getString(R.string.SORT_PREF_DEFAULT_VALUE))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu resource main as the menu for this activity
        new MenuInflater(this).inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            // start the settings activity intent
            case R.id.menu_action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            // use the default behavior
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<TMDBPage> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<TMDBPage>(MainActivity.this) {

            private boolean loading = false;

            @Override
            protected void onStartLoading() {
                // if it's loading already then we don't want to start a new one
                if (loading) {
                    return;
                }
                loading = true;

                forceLoad();
            }

            @Override
            public TMDBPage loadInBackground() {
                TMDBPage response = null;

                // get's the first page of Movies from TMDB based on the CURRENT_SORT_METHOD
                response = TMDBUtils.getResponseFromURL(TMDBUtils.buildUrl(1, CURRENT_SORT_METHOD));

                return response;
            }

            @Override
            public void deliverResult(TMDBPage page) {
                loading = false;
                super.deliverResult(page);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<TMDBPage> loader, TMDBPage page) {
        if (page != null) {
            adapter.setData(page.getResults());
        }
        //TODO handle null by clearing the adapter's data and showing an appropriate error
    }

    @Override
    public void onLoaderReset(Loader<TMDBPage> loader) {
        adapter.reset();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_CHANGED = true; // cache the fact that the preferences have changed
        // the onStart method will restart the loader which will grab the appropriate method
        // from the preferences manager
    }

    @Override
    public void onClick(TMDBPage.TMDBResult result) {
        // starts the movie details activitiy with the appropriate data
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, GsonFactory.INSTANCE.getGson().toJson(result));
        startActivity(intent);
    }
}
