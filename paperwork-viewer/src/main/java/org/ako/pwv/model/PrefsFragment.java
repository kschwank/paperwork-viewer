package org.ako.pwv.model;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import org.ako.pwv.R;

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_screen);
    }
}
