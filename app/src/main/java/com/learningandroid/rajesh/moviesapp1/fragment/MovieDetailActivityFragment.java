package com.learningandroid.rajesh.moviesapp1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learningandroid.rajesh.moviesapp1.R;
import com.learningandroid.rajesh.moviesapp1.adapter.MovieImageAdapter;
import com.learningandroid.rajesh.moviesapp1.data.MovieData;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailFragment = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        MovieData detailMovieData = getSerializableMovieData();

        TextView movieHeading = (TextView) detailFragment.findViewById(R.id.movie_name_heading);
        movieHeading.setText(detailMovieData.getTitle());

        ImageView moviePoster = (ImageView) detailFragment.findViewById(R.id.movie_detail_image);
        Picasso.with(getActivity())
                .load(MovieImageAdapter.buildImageUri(detailMovieData.getPosterPath()))
                .placeholder(R.drawable.movie_trailer)
                .into(moviePoster);

        TextView movieReleaseDate = (TextView) detailFragment.findViewById(R.id.movie_release_date);
        movieReleaseDate.setText(detailMovieData.getReleaseDate());

        TextView movieDuration = (TextView) detailFragment.findViewById(R.id.movie_duration);
        movieDuration.setText(getString(R.string.dummy_movie_duration));

        TextView movieRating = (TextView) detailFragment.findViewById(R.id.movie_rating);
        movieRating.setText(Double.toString(detailMovieData.getVoteAverage()) + "/10");

        TextView movieDescription = (TextView) detailFragment.findViewById(R.id.movie_description);
        movieDescription.setText(detailMovieData.getOverview());

        return detailFragment;
    }

    private MovieData getSerializableMovieData() {
        Intent intent = getActivity().getIntent();
        return (MovieData) intent.getSerializableExtra(getString(R.string.movie_serializable_constant));
    }
}
