package com.example.sony.popularmovies;

/**
 * Created by SONY on 7/4/2017.
 */

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;


public final class JSONParser {


    public static ArrayList<Movie> getSimpleMovieStringsFromJson(Context context, String movieJsonStr)

            throws JSONException {
        final String KEY_ID="id";
        final String KEY_POSTER_PATH="poster_path";
        final String KEY_TITLE="title";
        final String KEY_OVERVIEW="overview";
        final String KEY_VOTE="vote_count";
        final String KEY_RELEASE_DATE="release_date";
        final String query_section="results";
        if(movieJsonStr==null){
            return null;
        }
        else {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(query_section);

            ArrayList<Movie> parsedMovieData = new ArrayList<Movie>();
            parsedMovieData.clear();
            for (int i = 0; i < movieArray.length(); i++) {
                String id;
                String poster;
                String title;
                String synopsis;
                String rating;
                String release_date;
                JSONObject newData = movieArray.getJSONObject(i);
                id = newData.getString(KEY_ID);
                poster = newData.getString(KEY_POSTER_PATH);
                title = newData.getString(KEY_TITLE);
                synopsis = newData.getString(KEY_OVERVIEW);
                rating = newData.getString(KEY_VOTE);
                release_date = newData.getString(KEY_RELEASE_DATE);
                Movie x = new Movie();
                x.setOriginalTitle(title);
                x.setId(id);
                x.setPosterPath(poster);
                x.setOverview(synopsis);
                x.setRating(rating);
                x.setReleaseDate(release_date);
                parsedMovieData.add(x);

            }

            return parsedMovieData;
        }
    }

}