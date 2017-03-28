package com.jpgilchrist.android.popularmovies.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private String FETCH_NEXT_EXTRA = "FETCH_NEXT_EXTRA";

    private final TMDBPage data = new TMDBPage();

    private BroadcastReceiver fetchPageReceiver;

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
        adapter = new MovieGridAdapter(data);
        recyclerView.setAdapter(adapter);

        // setup and start the loader to fetch the TMDB Data
        final int loaderId = TMDB_LOADER_ID;
        final LoaderManager.LoaderCallbacks<TMDBPage> callback = MainActivity.this;
        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putBoolean(FETCH_NEXT_EXTRA, true);
        getSupportLoaderManager().initLoader(TMDB_LOADER_ID, bundleForLoader, MainActivity.this);

        fetchPageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Received Broadcast: " + intent.getAction());

                Bundle bundle = new Bundle();
                switch (intent.getAction()) {
                    case MovieGridAdapter.FETCH_NEXT_PAGE_BROADCAST:
                        bundle.putBoolean(FETCH_NEXT_EXTRA, true);
                        getSupportLoaderManager().restartLoader(TMDB_LOADER_ID, bundle, MainActivity.this);
                        break;
                    case MovieGridAdapter.FETCH_PREVIOUS_PAGE_BROADCAST:
                        bundle.putBoolean(FETCH_NEXT_EXTRA, false);
                        getSupportLoaderManager().restartLoader(TMDB_LOADER_ID, bundle, MainActivity.this);
                        break;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MovieGridAdapter.FETCH_NEXT_PAGE_BROADCAST);
        intentFilter.addAction(MovieGridAdapter.FETCH_PREVIOUS_PAGE_BROADCAST);
        registerReceiver(fetchPageReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(fetchPageReceiver);
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

    private int leadingPage = 0;
    private int trailingPage = 0;

    @Override
    public Loader<TMDBPage> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<TMDBPage>(MainActivity.this) {

            private boolean loading = false;

            @Override
            protected void onStartLoading() {
                Log.d(TAG, "onStartLoading: " + args.getBoolean(FETCH_NEXT_EXTRA));
                if (loading) {
                    return;
                }
                loading = true;

                forceLoad();
            }

            @Override
            public TMDBPage loadInBackground() {
                boolean fetchNext = args.getBoolean(FETCH_NEXT_EXTRA);
                TMDBPage response = null;
                if (fetchNext) {
                    Log.d(TAG, "Fetch Next " + (trailingPage + 1));
                    response = TMDBUtils.getResponseFromURL(TMDBUtils.buildPublicMoviesURL(trailingPage + 1));
                } else {
                    Log.d(TAG, "Fetch Previous " + (leadingPage - 1));
                    response = TMDBUtils.getResponseFromURL(TMDBUtils.buildPublicMoviesURL(leadingPage - 1));
                }

                return response;
            }

            @Override
            public void deliverResult(TMDBPage page) {
                Log.d(TAG, "deliverResult" + page);
                loading = false;
                super.deliverResult(page);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<TMDBPage> loader, TMDBPage page) {
        Log.d(TAG, "onLoadFinished leading[" + leadingPage + "] trailing[" + trailingPage + "] page[" + page.getPage() + "]");

        int fetched = page.getPage();
        boolean fetchedNext = false;

        if (fetched > trailingPage) {
            fetchedNext = true;
            trailingPage = fetched;
            if (leadingPage == 0) {
                leadingPage = fetched;
            }
        } else {
            fetchedNext = false;
            leadingPage = fetched;
            if (trailingPage == 0) {
                trailingPage = fetched;
            }
        }

        if (fetchedNext) {
            Log.d(TAG, "onLoadFinished appendPage " + page);
            adapter.appendPage(page);
        } else {
            Log.d(TAG, "onLoadFinished prependPage " + page);
            adapter.prependPage(page);
        }
    }

    @Override
    public void onLoaderReset(Loader<TMDBPage> loader) {
        adapter.setPage(null);
    }
}
