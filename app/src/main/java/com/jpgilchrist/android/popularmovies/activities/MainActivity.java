package com.jpgilchrist.android.popularmovies.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jpgilchrist.android.popularmovies.adapters.MovieGridAdapter;
import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<TMDBPage> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MovieGridAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private int TMDB_LOADER_ID = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

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

        getSupportLoaderManager().initLoader(TMDB_LOADER_ID, null, MainActivity.this);
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
                if (loading) {
                    return;
                }

                loading = true;

                forceLoad();
            }

            @Override
            public TMDBPage loadInBackground() {
                TMDBPage response = null;

                response = TMDBUtils.getResponseFromURL(TMDBUtils.buildPublicMoviesURL(1));

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
            adapter.appendPage(page);
        }
    }

    @Override
    public void onLoaderReset(Loader<TMDBPage> loader) {
        adapter.reset();
    }
}
