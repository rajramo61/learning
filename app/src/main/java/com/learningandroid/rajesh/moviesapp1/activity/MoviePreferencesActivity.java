package com.learningandroid.rajesh.moviesapp1.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.learningandroid.rajesh.moviesapp1.R;

public class MoviePreferencesActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private static String LOG_SORTING_ACTIVITY = MoviePreferencesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_SORTING_ACTIVITY, "Inside MoviePreferencesActivity");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindSortingPreferenceToValue(findPreference(getString(R.string.pref_key_sorting_scheme)));
    }

    private void bindSortingPreferenceToValue(Preference preference) {
        Log.i(LOG_SORTING_ACTIVITY, "Inside bindSortingPreferenceToValue");
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), getString(R.string.default_movie_sorting_scheme)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }
}
