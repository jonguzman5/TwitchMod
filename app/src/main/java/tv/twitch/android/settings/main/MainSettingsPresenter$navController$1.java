package tv.twitch.android.settings.main;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.settings.SettingsDestination;

public class MainSettingsPresenter$navController$1 {
    /* ... */

    public void navigateToSettingFragment(SettingsDestination settingsDestination, Bundle bundle) {
        Fragment fragment = new Fragment();

        /* ... */

        switch (6) {
            case 6:
                fragment = Hooks.getModSettingsFragment();
                break;
        }
        /* ... */

        fragment.equals(fragment);
    }

    /* ... */
}
