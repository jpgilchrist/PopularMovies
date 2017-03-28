package com.jpgilchrist.android.popularmovies.activities;

import android.content.Intent;
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

    private final TMDBPage data = new TMDBPage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Starting MainActivity");

        // find the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.movie_grid_recycler_view);

        // they will all be the same height and width
        recyclerView.setHasFixedSize(true);

        // create a new gird layout that is 2 wide
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // create a new adapter with our fake data
        adapter = new MovieGridAdapter(data);
        recyclerView.setAdapter(adapter);

        // setup and start the loader to fetch the TMDB Data
        int loaderId = TMDB_LOADER_ID;
        LoaderManager.LoaderCallbacks<TMDBPage> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
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

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public TMDBPage loadInBackground() {
                TMDBPage response = TMDBUtils.getResponseFromURL(TMDBUtils.buildPublicMoviesURL(1));

                return response;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<TMDBPage> loader, TMDBPage page) {
        adapter.setPage(page);
    }

    @Override
    public void onLoaderReset(Loader<TMDBPage> loader) {
        adapter.setPage(null);
    }
}
