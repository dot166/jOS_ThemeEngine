package jOS.ThemeEngine;

import static jOS.Core.ThemeEngine.ThemeEngine.getAllThemes;

import jOS.Core.jActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends jActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(R.layout.activity_main, false);
        super.onCreate(savedInstanceState);
        findViewById(R.id.loadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView resultView= (TextView) findViewById(R.id.res);

                resultView.setText(getAllThemes((jActivity) v.getContext()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.options, menu);
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
