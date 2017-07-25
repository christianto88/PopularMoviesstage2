package com.example.sony.popularmovies;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by SONY on 7/23/2017.
 */

public final  class CursorParser {
    public static ArrayList<Movie> parseCursor(Cursor a){
        ArrayList<Movie> parsedMovieData = new ArrayList<Movie>();
        int idIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int titleIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        int ratingIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);
        int posterIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        int releaseDateIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int overviewIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int favoriteIndex=a.getColumnIndex(MovieContract.MovieEntry.COLUMN_FAVORITE);
        while(a.moveToNext()){
            Movie y=new Movie();
            y.setReleaseDate(a.getString(releaseDateIndex));
            y.setRating(a.getString(ratingIndex));
            y.setFavorite(a.getString(favoriteIndex));
            y.setId(a.getString(idIndex));
            y.setOriginalTitle(a.getString(titleIndex));
            y.setOverview(a.getString(overviewIndex));
            y.setPosterPath(a.getString(posterIndex));
            parsedMovieData.add(y);
        }
        return parsedMovieData;
    }
}
