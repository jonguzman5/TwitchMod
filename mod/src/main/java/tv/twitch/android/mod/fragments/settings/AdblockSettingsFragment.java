package tv.twitch.android.mod.fragments.settings;


public class AdblockSettingsFragment extends BaseSettingsFragment {
    @Override
    public String getFragmentTag() {
        return "adblock_preferences";
    }

    @Override
    public String getXmlFilename() {
        return "mod_adblock_preferences";
    }
}
