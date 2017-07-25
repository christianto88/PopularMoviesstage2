package com.example.sony.popularmovies;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class favorites_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG ="" ;
    private TextView faves;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_activity);
        faves=(TextView)findViewById(R.id.favorite_text);
        getSupportLoaderManager().initLoader(11,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mMovieData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mMovieData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // COMPLETED (5) Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int idx=data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        while(data.moveToNext()){
            String movie=data.getString(idx);
            faves.append(movie+"\n");
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
