package com.jpgilchrist.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inject the Settings Fragment
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        // setup the action bar to have the leftward facing back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
