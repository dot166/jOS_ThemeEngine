package jOS.ThemeEngine;

import jOS.Core.jConfigActivity;

public class ConfigActivity extends jConfigActivity {
    @Override
    public int appName() {
        return R.string.config_name;
    }
    @Override
    public int appIcon() {
        return R.mipmap.ic_launcher;
    }
    @Override
    public int preferenceFragmentValue() {
        return R.string.config_fragment_name;
    }
    public static class jThemeEngineConfigFragment extends LauncherSettingsFragment {
        @Override
        public boolean isSDKConfig() {
            return true;
        }
        @Override
        public int preferenceXML() {
            return R.xml.themeengine_prefs;
        }
    }
}
