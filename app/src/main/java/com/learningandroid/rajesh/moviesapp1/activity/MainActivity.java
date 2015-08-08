package com.learningandroid.rajesh.moviesapp1.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.learningandroid.rajesh.moviesapp1.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.movie_sorting_menu) {
            Intent sortingIntent = new Intent(this, MoviePreferencesActivity.class);
            startActivity(sortingIntent);
            return true;
        }
        else if(id == R.id.grp_sort_by){
            Toast.makeText(this, "Group id", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.popular){
            Toast.makeText(this, "popular id", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.highest_rating){
            Toast.makeText(this, "rating id", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "no action", Toast.LENGTH_SHORT).show();
        }*/

        return super.onOptionsItemSelected(item);
    }
}
