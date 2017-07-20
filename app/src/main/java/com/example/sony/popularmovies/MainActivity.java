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
import android.text.TextUtils;
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
private static final int LOADER_ID=25;

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
//        if (savedInstanceState != null) {
//            String queryUrl = savedInstanceState.getParcelableArrayList(data);
//
//            mMovieAdapter.setMovieData().setText(queryUrl);
//        }
        loadMovieData("popular");
    }

    private void loadMovieData(String sort_category) {
        showMovieDataView();
        Bundle bundle=new Bundle();
        bundle.putString("sort",sort_category);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ArrayList<Movie>> MovieLoader = loaderManager.getLoader(LOADER_ID);
        if (MovieLoader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }

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
    public Loader<ArrayList<Movie>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
            //ArrayList<Movie> data;
            @Override
            public ArrayList<Movie> loadInBackground() {
                String movie=args.getString("sort");
                if(movie==null|| TextUtils.isEmpty(movie)){
                    return null;
                }
                URL movieRequestUrl = APIRequest.buildUrl(movie);

                try {
                    String jsonMovieResponse = APIRequest
                            .getResponseFromHttpUrl(movieRequestUrl);

                    ArrayList <Movie> simpleJsonMovieData = JSONParser
                            .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);

                    return simpleJsonMovieData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }



            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args==null){
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                }
//                 if (data != null) {
//                     deliverResult(data);
//                 } else {
//                     forceLoad();
//                 }
            }
//             @Override
//             public void deliverResult(ArrayList<Movie> x) {
//                 data=x;
//                 super.deliverResult(x);

//             }
        };


    }
    public boolean onlineStatus() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(onlineStatus()){
            if (data != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(data);
            } else {

                showErrorMessage(getString(com.example.sony.popularmovies.R.string.no_data_error));

            }
        }
        else
        {
            showErrorMessage(getString(com.example.sony.popularmovies.R.string.no_connection_error));
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

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
            loadMovieData("top_rated");
        }
        else if(R.id.popular==item.getItemId()){
            loadMovieData("popular");
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
