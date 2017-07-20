package com.example.sony.popularmovies;

/**
 * Created by SONY on 7/4/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieTextView = (TextView) findViewById(R.id.tv_display_weather);
        poster=(ImageView)findViewById(R.id.detail_poster) ;

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                movieData = (Movie) intentThatStartedThisActivity.getSerializableExtra("movie");
                Picasso.with(getApplicationContext()).load(movieData.getUrl()).into(poster);
                movieTextView.setText(getString(com.example.sony.popularmovies.R.string.original_title)+ movieData.getOriginalTitle()+getString(com.example.sony.popularmovies.R.string.synopsis)+ movieData.getOverview()+getString(com.example.sony.popularmovies.R.string.rating)+ movieData.getRating()+getString(com.example.sony.popularmovies.R.string.release_date)+ movieData.getReleaseDate());
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.favorites,menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(R.id.favorites==item.getItemId()){
//
//            if(item.isChecked()){
//                item.setIcon(R.drawable.ic_star_unchecked);
//                item.setChecked(false);
//                Toast.makeText(this, "UN FAVORITOS",
//                        Toast.LENGTH_LONG).show();
//            }else
//            {
//                item.setIcon(R.drawable.ic_star_check);
//                item.setChecked(true);
//                Toast.makeText(this, "FAVORITOS",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//        return true;
//    }
}