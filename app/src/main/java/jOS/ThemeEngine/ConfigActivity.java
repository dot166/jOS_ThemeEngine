package jOS.ThemeEngine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import java.util.Objects;

import jOS.Core.ActionBar2;
import jOS.Core.LIBAboutActivity;
import jOS.Core.jConfigActivity;
import jOS.Core.utils.ErrorUtils;
import jOS.Core.utils.IconUtils;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_favorite).setIcon(IconUtils.getActivityIcon(this));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            try {
                startActivity(new Intent(this, jConfigActivity.class));
            } catch (Exception e) {
                ErrorUtils.handle(e, this);
            }
            return true;
        } else if (itemId == R.id.action_favorite) {
            startActivity(new Intent(this, LIBAboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class jThemeEngineConfig {

        public static final String PREF_SHOW_APP_ICON = "pref_showAppIcon";
        public static final String PREF_THEME = "pref_theme";
        public static final String PREF_THEME_ENGINE_ENABLED = "pref_enableThemeEngine";

        @SuppressLint("ApplySharedPref")
        public static boolean readShowAppIcon(final SharedPreferences prefs,
                                              final Context context) {
            if (!prefs.contains(PREF_SHOW_APP_ICON)) {
                final ApplicationInfo appInfo = context.getApplicationInfo();
                final boolean isApplicationInSystemImage =
                    (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
                // Default value
                prefs.edit().putBoolean(PREF_SHOW_APP_ICON, !isApplicationInSystemImage).commit();
                return !isApplicationInSystemImage;
            }
            return prefs.getBoolean(PREF_SHOW_APP_ICON, false);
        }
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
                case jThemeEngineConfig.PREF_THEME:
                    Handler handler = new Handler();
                    handler.postDelayed(this::recreateActivityNow, 2000);
                    break;
                case jThemeEngineConfig.PREF_SHOW_APP_ICON:
                    TEBroadcastReceiver.toggleAppIcon(requireActivity());
                    break;
            }
        }
    }
}
