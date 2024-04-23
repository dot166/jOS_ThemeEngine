package jOS.ThemeEngine;

import static jOS.Core.ThemeEngine.currentTheme;
import static jOS.Core.ThemeEngine.getAllThemes;
import static jOS.Core.ThemeEngine.getThemeFromDB1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import jOS.Core.jActivity;

public class MainActivity extends jActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(R.layout.activity_main, true, true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
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

    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        // inserting complete table details in this text field
        TextView resultView= (TextView) findViewById(R.id.res);

        resultView.setText(getAllThemes(this));
    }
}