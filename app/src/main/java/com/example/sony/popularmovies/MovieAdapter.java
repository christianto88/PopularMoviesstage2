package com.example.sony.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>  {

    private ArrayList<Movie> mMovieData;
    public Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movieData);
    }


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context
    ) {
        mClickHandler = clickHandler;
        this.context=context;

    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder  implements OnClickListener {
        private ImageView thumb;
        public MovieAdapterViewHolder(View view) {
            super(view);
            thumb=(ImageView)view.findViewById(R.id.myImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movieDetail = mMovieData.get(adapterPosition);
            mClickHandler.onClick(movieDetail);
        }
        public ImageView getImageView() {
            return thumb;
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_detail, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MovieAdapterViewHolder MovieAdapterViewHolder, int position) {

        Picasso.with(context).load(mMovieData.get(position).getUrl()).into(MovieAdapterViewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    public void setMovieData(ArrayList<Movie> MovieData) {
        mMovieData = MovieData;
        notifyDataSetChanged();
    }
}
