package jOS.ThemeEngine;

import static jOS.Core.ThemeEngine.currentTheme;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Objects;

public class database extends ContentProvider {
    private static final String KEY_THEME = "pref_theme";
    private static final String KEY_THEMEENGINEENABLED = "pref_enablethemeengine";

    public database() {
    }

    // defining authority so that other application can access it
    static final String PROVIDER_NAME = "jOS.Core.ThemeEngine.database";

    // defining content URI
    static final String URL = "content://" + PROVIDER_NAME + "/themes";

    // parsing the content URI
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String id = "id";
    static final String name = "name";
    static final String current = "current";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    public static Context mContext;
    private static HashMap<String, String> values;

    static {

        // to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // to access whole table
        uriMatcher.addURI(PROVIDER_NAME, "themes", uriCode);

        // to access a particular row
        // of the table
        uriMatcher.addURI(PROVIDER_NAME, "themes/*", uriCode);
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/themes";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    // creating the database
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mContext = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (!Objects.equals(currentTheme, PreferenceManager.getDefaultSharedPreferences(getContext()).getString(KEY_THEME, "default"))) {
            ContentValues values = new ContentValues();

            for (int i = getContext().getResources().getStringArray(R.array.themesConfig).length - 1; i >= 0; i--) {
                String themeName = (String) Array.get(getContext().getResources().getStringArray(R.array.themesConfig), i);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String themeNamePrefs = prefs.getString(KEY_THEME, "Holo");
                currentValue = check_if_enabled(getContext(), themeName, themeNamePrefs);

                // fetching text from user
                values.put(database.name, themeName);

                // fetching text from user
                values.put(database.current, currentValue.toString());

                // print values to log
                Log.i("Database Update", String.valueOf(values));

                // inserting into database through content URI
                String where = "name=?";
                String[] whereArgs = new String[] {themeName};
                db.update(TABLE_NAME, values, where, whereArgs);
            }
        }
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = id;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    private static Boolean check_if_enabled(Context context, String themeName, String themeNamePrefs) {
        if (!PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_THEMEENGINEENABLED, true)) {
            return false;
        }
        return themeName.equals(themeNamePrefs);
    }

    // adding data to the database
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // creating object of database
    // to perform query
    private SQLiteDatabase db;

    // declaring name of the database
    static final String DATABASE_NAME = "themeDB";

    // declaring table name of the database
    static final String TABLE_NAME = "themes";

    // declaring version of the database
    static final int DATABASE_VERSION = 1;

    // sql query to create the table
    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT NOT NULL, "
            + " current BOOLEAN NOT NULL);";

    static Boolean currentValue;

    // creating a database
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // defining a constructor
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // creating a table in the database
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_DB_TABLE);

            // class to add values in the database
            ContentValues values = new ContentValues();

            for (int i = mContext.getResources().getStringArray(R.array.themesConfig).length - 1; i >= 0; i--) {
                String themeName = (String) Array.get(mContext.getResources().getStringArray(R.array.themesConfig), i);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                String themeNamePrefs = prefs.getString(KEY_THEME, "default");
                currentValue = check_if_enabled(mContext, themeName, themeNamePrefs);

                // fetching text from user
                values.put(database.name, themeName);

                // fetching text from user
                values.put(database.current, currentValue);

                // print values to log
                Log.i("Database init", String.valueOf(values));

                // inserting into database through content URI
                db.insert(TABLE_NAME, "", values);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // sql query to drop a table
            // having similar name
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
