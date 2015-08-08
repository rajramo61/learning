package com.learningandroid.rajesh.moviesapp1.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.learningandroid.rajesh.moviesapp1.R;
import com.learningandroid.rajesh.moviesapp1.data.MovieData;
import com.squareup.picasso.Picasso;

/**
 * Created by Rajesh Kumar Dwivedi on 7/28/15.
 */


public class MovieImageAdapter extends ArrayAdapter<MovieData> {

    private final static String SCHEME = "https";
    private final static String IMAGE_DOMAIN = "image.tmdb.org";
    private final static String IMAGE_PATH_1 = "t";
    private final static String IMAGE_PATH_2 = "p";
    private final static String DEFAULT_IMAGE_SIZE = "w185";

    private final String LOG_MOVIE_ADAPTER = MovieImageAdapter.class.getSimpleName();

    public MovieImageAdapter(Context context) {
        super(context, 0);
    }

    public static Uri buildImageUri(final String movieImageId) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(IMAGE_DOMAIN)
                .appendPath(IMAGE_PATH_1)
                .appendPath(IMAGE_PATH_2)
                .appendPath(DEFAULT_IMAGE_SIZE)
                .appendPath(movieImageId.substring(1))
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieData movieDataItem = getItem(position);//It's the type of Adapter
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.gridview_main, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_movie_imageview);
        //Picasso.with(getContext()).setLoggingEnabled(true);
        Uri imageUrl = buildImageUri(movieDataItem.getImagePath());
        if (Log.isLoggable(LOG_MOVIE_ADAPTER, Log.DEBUG))
            Log.d(LOG_MOVIE_ADAPTER, "Image URI to load = " + imageUrl.toString());
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.movie_trailer)
                .into(imageView);
        return convertView;
    }
}


/*
public class MovieImageAdapter extends ArrayAdapter<Integer> {

    private final String LOG_MOVIE_ADAPTER = MovieImageAdapter.class.getSimpleName();

    public MovieImageAdapter(Context context, List<Integer> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i(LOG_MOVIE_ADAPTER, "Inside getView");
        int item = getItem(position);//It's the type of Adapter
        //Log.i(LOG_MOVIE_ADAPTER, "Inside getView - after getItem");
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                            .inflate(R.layout.gridview_main, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_movie_imageview);
        //imageView.setImageResource(item);
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext())
                .load(item)
                .into(imageView);
        return convertView;
    }
}
*/
