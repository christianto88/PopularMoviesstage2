package com.example.sony.popularmovies;

/**
 * Created by SONY on 7/4/2017.
 */

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private Movie movieData;
    private TextView movieTextView;
    private ImageView poster;
    private MenuItem fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieTextView = (TextView) findViewById(R.id.tv_display_weather);
        poster=(ImageView)findViewById(R.id.detail_poster) ;
        fav=(MenuItem) findViewById(R.id.favorites);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                movieData = (Movie) intentThatStartedThisActivity.getSerializableExtra("movie");
                Picasso.with(getApplicationContext()).load(movieData.getUrl()).into(poster);
                movieTextView.setText(getString(com.example.sony.popularmovies.R.string.original_title)+ movieData.getOriginalTitle()+getString(com.example.sony.popularmovies.R.string.synopsis)+ movieData.getOverview()+getString(com.example.sony.popularmovies.R.string.rating)+ movieData.getRating()+getString(com.example.sony.popularmovies.R.string.release_date)+ movieData.getReleaseDate());
                if(movieData.getFavorite()=="favorite") {
//                    fav.setIcon(R.drawable.ic_star_check);
                    fav.setChecked(true);
                }
                else
                {
//                    fav.setIcon(R.drawable.ic_star_unchecked);
                    fav.setChecked(false);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.favorites,menu);

        return true;
    }
public void insertData(){
    ContentValues contentValues = new ContentValues();
    // Put the task description and selected mPriority into the ContentValues
    contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieData.getId());
    contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieData.getOriginalTitle());
    contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieData.getOverview());
    contentValues.put(MovieContract.MovieEntry.COLUMN_RATING, movieData.getRating());
    contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movieData.getPosterPath());
    contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieData.getReleaseDate());
    contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE,"favorite");

    Uri x=MovieContract.MovieEntry.CONTENT_URI;
    x.buildUpon().appendPath(movieData.getId());

    Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

    // COMPLETED (8) Display the URI that's returned with a Toast
    // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
    if(uri != null) {
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    // Finish activity (this returns back to MainActivity)
    finish();
}
public void updateData(){
    ContentValues contentValues = new ContentValues();
    contentValues.put("favorite","favorite");
    Uri x=MovieContract.MovieEntry.CONTENT_URI;
    x.buildUpon().appendPath(movieData.getId());
    int f=getContentResolver().update(x,contentValues,movieData.getId(),null);

    if(f != 0) {
        Toast.makeText(getBaseContext(), Integer.toString(f), Toast.LENGTH_LONG).show();

    }
    finish();
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.favorites==item.getItemId()){

            if(item.isChecked()){
                //update data
                item.setIcon(R.drawable.ic_star_unchecked);
                item.setChecked(false);
                Toast.makeText(this, "UN FAVORITOS",
                        Toast.LENGTH_SHORT).show();
                updateData();
            }else
            {
                item.setIcon(R.drawable.ic_star_check);
                item.setChecked(true);
                //masukan data baru
                insertData();
            }
        }
        else if(android.R.id.home==item.getItemId()){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}