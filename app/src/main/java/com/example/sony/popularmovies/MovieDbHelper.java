package com.example.sony.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY on 7/19/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String DATABASE_NAME = "movie.db";

    private static final int DATABASE_VERSION = 1;
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE =

                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +

                        MovieContract.MovieEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL,"                  +

                        MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE   + " VARCHAR(100) NOT NULL, "                    +
                        MovieContract.MovieEntry.COLUMN_OVERVIEW   + " VARCHAR(150) NOT NULL, "                    +

                        MovieContract.MovieEntry.COLUMN_RATING   + " REAL NOT NULL, "                    +
                        MovieContract.MovieEntry.COLUMN_RELEASE_DATE   + " VARCHAR(100) NOT NULL, "                    +

                        MovieContract.MovieEntry.COLUMN_FAVORITE + " VARCHAR(10) NOT NULL, "        +

                        " UNIQUE (" + MovieContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }


}
