package jOS.ThemeEngine;

import static jOS.Core.ActionBar.actionBarConfig;
import static jOS.Core.ThemeEngine.currentTheme;
import static jOS.Core.ThemeEngine.getAllThemes;
import static jOS.Core.ThemeEngine.getThemeFromDB1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import jOS.Core.jActivity;

public class MainActivity extends jActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(R.string.app_name_short, R.layout.activity_main, true, R.mipmap.ic_launcher, "jOS.ThemeEngine.CONFIG");
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        // inserting complete table details in this text field
        TextView resultView= (TextView) findViewById(R.id.res);

        resultView.setText(getAllThemes(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Objects.equals(currentTheme, getThemeFromDB1(this))) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
}