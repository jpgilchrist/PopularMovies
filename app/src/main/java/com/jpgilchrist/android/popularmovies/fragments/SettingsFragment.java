package com.jpgilchrist.android.popularmovies.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.jpgilchrist.android.popularmovies.R;

/**
 * Created by jpegz on 4/2/17.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the prefence screen from xml resource preferences.xml
        addPreferencesFromResource(R.xml.preferences);
    }
}
