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
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<TMDBPage>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MovieGridAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int TMDB_LOADER_ID = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.movie_grid_recycler_view);

        // they will all be the same height and width
        recyclerView.setHasFixedSize(true);

        // create a new gird layout that is 2 wide
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // create a new adapter with our fake data
        adapter = new MovieGridAdapter();
        recyclerView.setAdapter(adapter);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        getSupportLoaderManager().initLoader(TMDB_LOADER_ID, null, MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (PREFERENCES_HAVE_CHANGED) {
            PREFERENCES_HAVE_CHANGED = false;

            readSharedPreferencesForSortOrder();
            getSupportLoaderManager().restartLoader(TMDB_LOADER_ID, null, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void readSharedPreferencesForSortOrder() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        CURRENT_SORT_METHOD = TMDBUtils.Sort.fromSortValue(
                sharedPreferences.getString(getString(R.string.SORT_PREF_KEY), getString(R.string.SORT_PREF_DEFAULT_VALUE))
        );
    }

    private static TMDBUtils.Sort CURRENT_SORT_METHOD = TMDBUtils.Sort.POPULAR;

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
                if (loading) {
                    return;
                }

                loading = true;

                forceLoad();
            }

            @Override
            public TMDBPage loadInBackground() {
                TMDBPage response = null;

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
    }

    @Override
    public void onLoaderReset(Loader<TMDBPage> loader) {
        adapter.reset();
    }

    private static boolean PREFERENCES_HAVE_CHANGED = false;
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_CHANGED = true;
    }
}
