package main.soakim.no.birthdaymessenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;

import java.util.Locale;

/**
 * Created by Sondre on 23.10.2014.
 */
public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        EditTextPreference edit = (EditTextPreference) getPreferenceScreen().findPreference("message_preference");
        edit.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object changedValue) {
                if(changedValue == null || changedValue.equals("") || changedValue.toString().trim().equals("")) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sharedPref.edit().putString("default_message", changedValue.toString());
                    ((EditTextPreference)preference).setText(getString(R.string.default_message)+"");
                    return false;
                }
                return true;
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String lang = sharedPref.getString("language_preferance", "no");
        ListPreference languagelist = (ListPreference) getPreferenceScreen().findPreference("language_preferance");

        languagelist.setValueIndex(getLanguageIndex(lang));// check whats saved
        languagelist.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                setLanguage(newValue.toString());
                restartActivity();
                return true;
            }
        });
    }

    private int getLanguageIndex(String lang) {
        int index = 0;
        if(lang.equals("no")) index = 0;
        else if(lang.equals("en")) index = 1;
        return index;
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
    }

    private void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
}
