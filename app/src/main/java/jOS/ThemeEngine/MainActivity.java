package jOS.ThemeEngine;

import jOS.Core.jActivity;
import jOS.Core.utils.ErrorUtils;
import jOS.Core.utils.IconUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends jActivity {

    public static CheckBox mSwitchHideFallback;
    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(R.layout.activity_main, false);
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        Activity context = this;
        inflater.inflate(R.menu.options, menu);
        IconUtils.HideFallback = preferences.getBoolean("hideFallback", true);
        mSwitchHideFallback = Objects.requireNonNull(menu.findItem(R.id.app_bar_switch).getActionView()).findViewById(R.id.switchAB);
        mSwitchHideFallback.setChecked(IconUtils.HideFallback);
        mSwitchHideFallback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                IconUtils.HideFallback = b;
                preferences.edit().putBoolean("hideFallback", b).apply();
                try {
                    ArrayList<IconUtils.Icon> icons = IconUtils.ParseIconPack(context, "jOS.ThemeEngine", getResources());
                    icons.add(new IconUtils.Icon(AppCompatResources.getDrawable(context, jOS.Core.R.drawable.ic_launcher_j), getComponentName()));
                    IconsListFragment.iconListRecyclerAdapter.setIcons(icons);
                } catch (Exception e) {
                    ErrorUtils.handle(e, context);
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options:
                // User chooses the "Settings" item. Show the app settings UI.
                startActivity(new Intent("jOS.ThemeEngine.CONFIG"));
                return true;

            //case R.id.action_favorite:
            //// User chooses the "Favorite" action. Mark the current item as a
            //// favorite.
            //return true;

            default:
                // The user's action isn't recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
