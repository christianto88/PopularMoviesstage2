package com.example.sony.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SONY on 7/19/2017.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.sony.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

        public static final class MovieEntry implements BaseColumns {

            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MOVIE)
                    .build();

            public static final String TABLE_NAME = "movie";

            public static final String COLUMN_MOVIE_ID = "movie_id";
            public static final String COLUMN_ORIGINAL_TITLE="original_title";
            public static final String COLUMN_OVERVIEW="overview";
            public static final String COLUMN_RATING="rating";
            public static final String COLUMN_RELEASE_DATE="release_date";
            public static final String COLUMN_FAVORITE="favorite";

        }
}
