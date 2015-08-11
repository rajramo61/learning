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

import java.io.UnsupportedEncodingException;


/**
 * This class Just reads the model data and populates the fragment with using that data.
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
        TextView movieDescription = (TextView) detailFragment.findViewById(R.id.movie_description);

        try {
            movieHeading.setText(new String(detailMovieData.getTitle().getBytes(), "UTF-8"));
            movieDescription.setText(new String(detailMovieData.getOverview().getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ImageView moviePoster = (ImageView) detailFragment.findViewById(R.id.movie_detail_image);
        Picasso.with(getActivity())
                .load(MovieImageAdapter.buildImageUri(detailMovieData.getPosterPath()))
                .placeholder(R.drawable.movie_trailer)
                .into(moviePoster);

        TextView movieReleaseDate = (TextView) detailFragment.findViewById(R.id.movie_release_date);
        movieReleaseDate.setText(detailMovieData.getReleaseDate());

        //This is just a placeholder with hard coded data.
        //This field will be used in next stage of the app.
        TextView movieDuration = (TextView) detailFragment.findViewById(R.id.movie_duration);
        movieDuration.setText(getString(R.string.dummy_movie_duration));

        TextView movieRating = (TextView) detailFragment.findViewById(R.id.movie_rating);
        movieRating.setText(Double.toString(detailMovieData.getVoteAverage()) + "/10");



        return detailFragment;
    }

    private MovieData getSerializableMovieData() {
        Intent intent = getActivity().getIntent();
        return (MovieData) intent.getSerializableExtra(getString(R.string.movie_serializable_constant));
    }
}
