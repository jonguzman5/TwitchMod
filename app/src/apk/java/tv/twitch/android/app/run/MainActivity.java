package tv.twitch.android.app.run;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.fragments.settings.MainSettingsFragment;
import tv.twitch.android.mod.utils.FragmentUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, new MainSettingsFragment());
        transaction.commit();
    }
}
