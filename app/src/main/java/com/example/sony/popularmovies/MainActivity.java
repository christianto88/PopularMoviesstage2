package com.example.sony.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,MovieAdapter.MovieAdapterOnClickHandler,LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this,getApplicationContext());
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask().execute("popular");

    }
    @Override
    public void onClick(Movie data) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", data);
        startActivity(intentToStartDetailActivity);
    }

    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String errMess) {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(errMess);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
//        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
//            @Override
//            public ArrayList<Movie> loadInBackground() {
////                URL movieRequestUrl = APIRequest.buildUrl(movie);
//
//                try {
//                    String jsonMovieResponse = APIRequest
//                            .getResponseFromHttpUrl(movieRequestUrl);
//
//                    ArrayList <Movie> simpleJsonMovieData = JSONParser
//                            .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);
//
//                    return simpleJsonMovieData;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }


    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie> >{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        public boolean onlineStatus() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null &&
                    cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (params.length == 0) {

                return null;
            }


            String movie = params[0];

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> MovieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(onlineStatus()){
                if (MovieData != null) {
                    showMovieDataView();
                    mMovieAdapter.setMovieData(MovieData);
                } else {

                    showErrorMessage(getString(com.example.sony.popularmovies.R.string.no_data_error));

                }
            }
            else
            {
               showErrorMessage(getString(com.example.sony.popularmovies.R.string.no_connection_error));
            }

        }
    }
    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);

        popup.getMenuInflater().inflate(R.menu.sorting, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (R.id.top_rated==item.getItemId()){
            new FetchMovieTask().execute("top_rated");
        }
        else if(R.id.popular==item.getItemId()){
            new FetchMovieTask().execute("popular");
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }
    public boolean onMenuItemSelect(MenuItem item) {
        showPopup(findViewById(item.getItemId()));
        return true;
    }


}