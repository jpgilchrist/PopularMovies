package com.jpgilchrist.android.popularmovies.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jpgilchrist.android.popularmovies.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // setup the action bar to have the leftward facing back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
