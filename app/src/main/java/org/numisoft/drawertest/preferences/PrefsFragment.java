package org.numisoft.drawertest.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import org.numisoft.drawertest.R;

/**
 * Created by mac-200 on 12/12/17.
 */

public class PrefsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

}
