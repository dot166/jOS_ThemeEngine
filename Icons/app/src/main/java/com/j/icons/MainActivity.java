package com.j.icons;

import jOS.Core.jActivity;

import android.os.Bundle;


public class MainActivity extends jActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(R.string.app_name, R.layout.activity_main, false);
        super.onCreate(savedInstanceState);
    }
}