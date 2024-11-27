package jOS.ThemeEngine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.preference.PreferenceManager;

import java.util.Objects;

import jOS.Core.ActionBar2;
import jOS.Core.jConfigActivity;

public class ConfigActivity extends jConfigActivity {
    @Override
    public jLIBSettingsFragment preferenceFragment() {
        return new jThemeEngineConfigFragment();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar2 actionBar2 = findViewById(jOS.Core.R.id.actionbar);
        actionBar2.setTitleCentered(true);
    }

    public static class jThemeEngineConfig {

        public static final String PREF_DARK_THEME = "pref_darkThemeEnabled";
        public static final String PREF_THEME = "pref_theme";
        public static final String PREF_THEME_ENGINE_ENABLED = "pref_enableThemeEngine";
    }

    public static class jThemeEngineConfigFragment extends jLIBSettingsFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public boolean isTEConfig() {
            return true;
        }
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            super.onCreatePreferences(savedInstanceState, rootKey);
            PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this);
        }
        @Override
        public int preferenceXML() {
            return R.xml.themeengine_prefs;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            switch (Objects.requireNonNull(key)) {
                case jThemeEngineConfig.PREF_THEME_ENGINE_ENABLED:
                case jThemeEngineConfig.PREF_DARK_THEME:
                case jThemeEngineConfig.PREF_THEME:
                    Handler handler = new Handler();
                    handler.postDelayed(this::recreateActivityNow, 2000);
                    break;
            }
        }
    }
}
