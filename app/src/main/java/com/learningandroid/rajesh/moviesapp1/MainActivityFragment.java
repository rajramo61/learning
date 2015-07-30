package com.learningandroid.rajesh.moviesapp1;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.learningandroid.rajesh.moviesapp1.data.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final static String PARAM_VALUE_API_KEY = "3ac2601d1000dffd84f90e37f687232d";
    private final static String PARAM_KEY_API_KEY = "api_key";
    private final static String SCHEME = "https";
    private final static String MOVIE_DOMAIN = "api.themoviedb.org";
    private final static String PATH_MOVIE_2 = "discover";
    private final static String PATH_MOVIE_1 = "3";
    private final static String PATH_MOVIE_3 = "movie";
    private final static String PARAM_VALUE_MOVIE_SORT_BY_POPULARITY = "popularity.desc";
    private final static String PARAM_KEY_MOVIE_SORT_BY_POPULARITY = "sort_by";
    private final static String IMAGE_DOMAIN = "image.tmdb.org";
    private final static String IMAGE_PATH_1 = "t";
    private final static String IMAGE_PATH_2 = "p";
    private final static String DEFAULT_IMAGE_SIZE = "W185";

    private final String LOG_MAINACTIVITY = MainActivityFragment.class.getSimpleName();

    private MovieImageAdapter movieImageAdapter;

    private List<Integer> imageList = Arrays.asList(R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer
                                        ,R.drawable.movie_trailer, R.drawable.movie_trailer);


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_MAINACTIVITY, "inside on create");
        MovieDetailsAsync movieDetailsAsync = new MovieDetailsAsync();
        movieDetailsAsync.execute();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_MAINACTIVITY, "inside onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.i(LOG_MAINACTIVITY, " from onCreateView - got the root view");
        movieImageAdapter = new MovieImageAdapter(getActivity(), imageList);
        Log.i(LOG_MAINACTIVITY, " from onCreateView - movieImageAdapter");
        GridView gridView = (GridView)rootView.findViewById(R.id.image_gridView);
        Log.i(LOG_MAINACTIVITY, " from onCreateView - before setting the adaper");
        gridView.setAdapter(movieImageAdapter);
        Log.i(LOG_MAINACTIVITY, "returning from onCreateView");
        return rootView;
    }

    private Uri buildMovieUri(){
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(MOVIE_DOMAIN)
                .appendPath(PATH_MOVIE_1)
                .appendPath(PATH_MOVIE_2)
                .appendPath(PATH_MOVIE_3)
                .appendQueryParameter(PARAM_KEY_MOVIE_SORT_BY_POPULARITY, PARAM_VALUE_MOVIE_SORT_BY_POPULARITY)
                .appendQueryParameter(PARAM_KEY_API_KEY, PARAM_VALUE_API_KEY)
                .build();
    }

    private Uri buildImageUri(final String movieId){
        //TODO - Implement this method to get the image url for the passed id
        return null;
    }

    private class MovieDetailsAsync extends AsyncTask<String, Void, List<MovieData>>{

        private final String LOG_MOVIE_ASYNC = MovieDetailsAsync.class.getSimpleName();

        @Override
        protected List<MovieData> doInBackground(String... params) {

            final String jsonData = readMovieJsonDataFromUrl();
            return getMovieDetails(jsonData);
        }

        @Override
        protected void onPostExecute(List<MovieData> result) {
            for(MovieData movieData : result){
                Log.i(LOG_MOVIE_ASYNC, "Movie DATA = " + movieData.toString());
            }
        }

        /**
         *
         * @return - Returns json string from the Movie DB API
         */
        private String readMovieJsonDataFromUrl(){
            InputStream inputStream = null;
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(buildMovieUri().toString());
                Log.i(LOG_MOVIE_ASYNC, "Movie URL = " + url.toString());
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
            Log.i(LOG_MOVIE_ASYNC, "JSON DATA = " + jsonData);
            return jsonData;
        }

        /**
         *
         * @param jsonData - String of JSON data retrieved from Movie DB api
         * @return - Returns the list of Movie data after parsing the json string
         */
        private List<MovieData> getMovieDetails(final String jsonData){
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
            final String MOVIE_ID = "id";
            final String MOVIE_IMAGE_PATH = "backdrop_path";
            final String MOVIE_TITLE = "original_title";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_POPULARITY = "popularity";
            final String MOVIE_USER_RATING = "vote_average";

            MovieData movieData = new MovieData();

            try {
                movieData.setMovieId(resultJsonObj.getLong(MOVIE_ID));
                movieData.setTitle(resultJsonObj.getString(MOVIE_TITLE));
                movieData.setOverview(resultJsonObj.getString(MOVIE_OVERVIEW));
                movieData.setImagePath(resultJsonObj.getString(MOVIE_IMAGE_PATH));
                movieData.setPopularity(resultJsonObj.getDouble(MOVIE_POPULARITY));
                movieData.setVoteAverage(resultJsonObj.getDouble(MOVIE_USER_RATING));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return movieData;
        }
    }
}
