package tv.twitch.android.settings;


import androidx.appcompat.app.AppCompatActivity;
import tv.twitch.android.mod.bridge.preference.Preference;
import tv.twitch.android.mod.bridge.preference.PreferenceFragmentCompat;

import tv.twitch.android.mod.hooks.Controller;


public final class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback { // TODO: __IMPLEMENT

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) { // TODO: __INJECT_METHOD
        return Controller.onPreferenceStartFragment(this, caller, pref);
    }
}
