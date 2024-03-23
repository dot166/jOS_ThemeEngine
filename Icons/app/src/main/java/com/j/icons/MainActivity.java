package com.j.icons;

import static jOS.Core.ThemeEngine.getSystemTheme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import jOS.Core.ActionBar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getSystemTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar.actionBarConfig(R.string.app_name, jOS.Core.R.drawable.ic_launcher_j, false, this, "");
    }
}