package jOS.ThemeEngine;

import jOS.Core.jConfigActivity;

public class ConfigActivity extends jConfigActivity {
    @Override
    public int preferenceFragmentValue() {
        return R.string.config_fragment_name;
    }
    public static class jThemeEngineConfigFragment extends LauncherSettingsFragment {
        @Override
        public boolean isLIBConfig() {
            return true;
        }
        @Override
        public int preferenceXML() {
            return R.xml.themeengine_prefs;
        }
    }
}
