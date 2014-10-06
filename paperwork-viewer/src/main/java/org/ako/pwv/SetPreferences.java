package org.ako.pwv;

import android.app.Activity;
import android.os.Bundle;
import org.ako.pwv.model.PrefsFragment;

public class SetPreferences extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }
}
