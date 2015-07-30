package com.learningandroid.rajesh.moviesapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rajesh Kumar Dwivedi on 7/28/15.
 */
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
