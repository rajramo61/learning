package com.learningandroid.rajesh.moviesapp1.adapter;

import android.content.Context;
import android.content.res.Resources;
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


public class MovieImageAdapter extends ArrayAdapter<MovieData> {

    private final String LOG_MOVIE_ADAPTER = MovieImageAdapter.class.getSimpleName();

    private static Resources resources;
    public MovieImageAdapter(Context context) {
        super(context, 0);
        resources = context.getResources();
    }

    public static Uri buildImageUri(final String movieImageId) {
        return new Uri.Builder()
                .scheme(resources.getString(R.string.SCHEME))
                .authority(resources.getString(R.string.IMAGE_DOMAIN))
                .appendPath(resources.getString(R.string.IMAGE_PATH_1))
                .appendPath(resources.getString(R.string.IMAGE_PATH_2))
                .appendPath(resources.getString(R.string.DEFAULT_IMAGE_SIZE))
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
        if (Log.isLoggable(LOG_MOVIE_ADAPTER, Log.DEBUG)) Log.d(LOG_MOVIE_ADAPTER, "Image URI to load = " + imageUrl.toString());
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.movie_trailer)
                .into(imageView);
        return convertView;
    }
}

