package main.soakim.no.birthdaymessenger;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.ContextMenu;
import android.view.View;

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
                    ((EditTextPreference)preference).setText(getString(R.string.default_message)+"");
                    return false;
                }
                return true;
            }
        });


    }
}
