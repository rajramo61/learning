package com.learningandroid.rajesh.moviesapp1.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.learningandroid.rajesh.moviesapp1.R;
import com.learningandroid.rajesh.moviesapp1.activity.MovieDetailActivity;
import com.learningandroid.rajesh.moviesapp1.activity.MoviePreferencesActivity;
import com.learningandroid.rajesh.moviesapp1.adapter.MovieImageAdapter;
import com.learningandroid.rajesh.moviesapp1.data.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    //TODO - move all string constatnts to strings.xml
    private final static String PARAM_VALUE_API_KEY = "";
    private final static String PARAM_KEY_API_KEY = "api_key";
    private final static String SCHEME = "https";
    private final static String MOVIE_DOMAIN = "api.themoviedb.org";
    private final static String PATH_MOVIE_2 = "discover";
    private final static String PATH_MOVIE_1 = "3";
    private final static String PATH_MOVIE_3 = "movie";
    private final static String PARAM_VALUE_MOVIE_SORT_BY_POPULARITY = "popularity.desc";
    private final static String PARAM_VALUE_MOVIE_SORT_BY_HIGHEST_RATING = "vote_average.desc";
    private final static String PARAM_KEY_MOVIE_SORT_BY_POPULARITY = "sort_by";


    private final String LOG_MAIN_ACTIVITY = MainActivityFragment.class.getSimpleName();

    private MovieImageAdapter movieImageAdapter;
    private List<MovieData> movieDataList;

   /* private List<Integer> imageList = Arrays.asList(R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer);*/


    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovieData(null);
    }

    private void fetchMovieData(final String sortingType) {
        Log.i(LOG_MAIN_ACTIVITY, "Inside fetchMovieData");
        String sortingScheme;
        if (sortingType == null)
            sortingScheme = getSortingPreference();
        else
            sortingScheme = sortingType;

        MovieDetailsAsync movieDetailsAsync = new MovieDetailsAsync();
        movieDetailsAsync.execute(sortingScheme);

    }

    private String getSortingPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return sharedPreferences.getString(getString(R.string.pref_key_sorting_scheme),
                getString(R.string.default_movie_sorting_scheme));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_MAIN_ACTIVITY, "inside on create");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i(LOG_MAIN_ACTIVITY, "inside onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        movieImageAdapter = new MovieImageAdapter(getActivity());
        GridView gridView = (GridView)rootView.findViewById(R.id.image_gridView);
        gridView.setAdapter(movieImageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentForDetail = new Intent(getActivity(), MovieDetailActivity.class);
                intentForDetail.putExtra(getString(R.string.movie_serializable_constant), movieDataList.get(position));
                startActivity(intentForDetail);
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.i(LOG_MAIN_ACTIVITY, "Inside onOptionsItemSelected");
        if (Log.isLoggable(LOG_MAIN_ACTIVITY, Log.DEBUG))
            Log.d(LOG_MAIN_ACTIVITY, "Inside onOptionsItemSelected");
        int selectedMenuId = menuItem.getItemId();
        if (selectedMenuId == R.id.movie_sorting_preference_menu) {
            Intent sortingIntent = new Intent(getActivity(), MoviePreferencesActivity.class);
            startActivity(sortingIntent);
            return true;
        }
        switch (selectedMenuId) {
            case R.id.popular:
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                fetchMovieData(PARAM_VALUE_MOVIE_SORT_BY_POPULARITY);
                return true;
            case R.id.highest_rating:
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                fetchMovieData(PARAM_VALUE_MOVIE_SORT_BY_HIGHEST_RATING);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_by, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private Uri buildMovieUri(final String sortingScheme) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(MOVIE_DOMAIN)
                .appendPath(PATH_MOVIE_1)
                .appendPath(PATH_MOVIE_2)
                .appendPath(PATH_MOVIE_3)
                .appendQueryParameter(PARAM_KEY_MOVIE_SORT_BY_POPULARITY, sortingScheme)
                .appendQueryParameter(PARAM_KEY_API_KEY, PARAM_VALUE_API_KEY)
                .build();
    }


    private class MovieDetailsAsync extends AsyncTask<String, Void, List<MovieData>>{

        private final String LOG_MOVIE_ASYNC = MovieDetailsAsync.class.getSimpleName();

        @Override
        protected List<MovieData> doInBackground(String... params) {
            Log.i(LOG_MOVIE_ASYNC, "Inside doInBackground");
            final String sortingScheme = params[0];
            final String jsonData = readMovieJsonDataFromUrl(sortingScheme);
            Log.i(LOG_MOVIE_ASYNC, "Inside doInBackground before return.");
            return getMovieDetails(jsonData);
        }

        @Override
        protected void onPostExecute(List<MovieData> result) {
            //Log.i(LOG_MOVIE_ASYNC, "Inside onPostExecute");
            movieDataList = result;
            if (movieDataList != null) {
                movieImageAdapter.clear();
                for (MovieData movieData : result) {
                    movieImageAdapter.add(movieData);
                    if (Log.isLoggable(LOG_MOVIE_ASYNC, Log.DEBUG))
                        Log.d(LOG_MOVIE_ASYNC, "Movie DATA = " + movieData.toString());
                }
            } else {
                Log.e(LOG_MOVIE_ASYNC, "Something went WRONG: No result is back from API call.");
            }
        }

        /**
         *
         * @return - Returns json string from the Movie DB API
         */
        private String readMovieJsonDataFromUrl(final String sortingScheme) {
            //Log.i(LOG_MOVIE_ASYNC, "Inside readMovieJsonDataFromUrl");
            InputStream inputStream = null;
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(buildMovieUri(sortingScheme).toString());
                if (Log.isLoggable(LOG_MOVIE_ASYNC, Log.DEBUG))
                    Log.d(LOG_MOVIE_ASYNC, "Movie URL = " + url.toString());
                inputStream = url.openStream();
                int cp;
                while ((cp = inputStream.read()) != -1) {
                    sb.append((char) cp);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String jsonData = sb.toString().trim();
            Log.i(LOG_MOVIE_ASYNC, jsonData);
            if (Log.isLoggable(LOG_MOVIE_ASYNC, Log.DEBUG))
                Log.d(LOG_MOVIE_ASYNC, "JSON DATA = " + jsonData);
            return jsonData;
        }

        /**
         *
         * @param jsonData - String of JSON data retrieved from Movie DB api
         * @return - Returns the list of Movie data after parsing the json string
         */
        private List<MovieData> getMovieDetails(final String jsonData){
            //Log.i(LOG_MOVIE_ASYNC, "Inside getMovieDetails");
            final String RESULTS = "results";
            final List<MovieData> movieDataList = new ArrayList<MovieData>();

            JSONObject forecastJson = null;
            try {
                forecastJson = new JSONObject(jsonData);
                JSONArray resultsArray = forecastJson.getJSONArray(RESULTS);

                int totalMovies = resultsArray.length();
                for(int index = 0; index < totalMovies; index++){
                    MovieData movieData = createMovieData(resultsArray.getJSONObject(index));
                    movieDataList.add(movieData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieDataList;
        }

        /**
         *
         * @param resultJsonObj
         * @return - Returns Movie data object from the passed json object
         */
        private MovieData createMovieData(JSONObject resultJsonObj) {
            //Log.i(LOG_MOVIE_ASYNC, "Inside getMovieDetails");
            final String MOVIE_ID = "id";
            final String MOVIE_IMAGE_PATH = "poster_path";//backdrop_path
            final String MOVIE_TITLE = "original_title";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_DURATION = "runtime";
            final String MOVIE_POPULARITY = "popularity";
            final String MOVIE_USER_RATING = "vote_average";
            final String MOVIE_RELEASE_DATE = "release_date";
            final String MOVIE_POSTER_IMAGE_PATH = "poster_path"; //For the detailed activity

            MovieData movieData = new MovieData();

            try {
                movieData.setMovieId(resultJsonObj.getLong(MOVIE_ID));
                movieData.setTitle(resultJsonObj.getString(MOVIE_TITLE));
                movieData.setOverview(resultJsonObj.getString(MOVIE_OVERVIEW));
                movieData.setImagePath(resultJsonObj.getString(MOVIE_IMAGE_PATH));
                movieData.setPopularity(resultJsonObj.getDouble(MOVIE_POPULARITY));
                movieData.setVoteAverage(resultJsonObj.getDouble(MOVIE_USER_RATING));
                movieData.setReleaseDate(resultJsonObj.getString(MOVIE_RELEASE_DATE));
                movieData.setPosterPath(resultJsonObj.getString(MOVIE_POSTER_IMAGE_PATH));
                movieData.setReleaseDate(resultJsonObj.getString(MOVIE_RELEASE_DATE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieData;
        }
    }
}