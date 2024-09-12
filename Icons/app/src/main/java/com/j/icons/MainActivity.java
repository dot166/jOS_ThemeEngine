package com.j.icons;

import jOS.Core.ActionBar2;
import jOS.Core.jActivity;
import jOS.Core.utils.ErrorUtils;
import jOS.Core.utils.IconUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends jActivity {

    public static CheckBox mSwitchHideFallback;
    SharedPreferences preferences;

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
                    ArrayList<IconUtils.Icon> icons = IconUtils.ParseIconPack(context, "com.j.icons", getResources());
                    icons.add(new IconUtils.Icon("jOS System Component", AppCompatResources.getDrawable(context, jOS.Core.R.drawable.ic_launcher_j), getComponentName(), context));
                    IconsListFragment.iconListRecyclerAdapter.setIcons(icons);
                } catch (Exception e) {
                    ErrorUtils.handle(e, context);
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
