package org.aitek.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import org.aitek.controller.R;
import org.aitek.controller.core.Movie;
import org.aitek.controller.mede8er.Mede8erCommander;
import org.aitek.controller.utils.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: andrea
 * Date: 8/16/13
 * Time: 6:23 PM
 */
public class MovieDetailActivity extends Activity {

    private Movie movie;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.full_screen_view);
        int position = (Integer) intent.getExtras().get(MovieDetailActivity.class.getName());

        try {
            movie = Mede8erCommander.getInstance(this).getMoviesManager().getMovie(position);
            imageView.setImageBitmap(movie.getImage());
        }
        catch (Exception e) {
            Logger.toast("Error: " + e.getMessage(), this);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_settings) {

            try {
                Intent mediaPLayerIntent = new Intent(getApplicationContext(), MoviePlayerActivity.class);
                startActivity(mediaPLayerIntent);
            }
            catch (Exception e) {
                Logger.toast("An error occurred trying to play the movie: " + e.getMessage(), getApplicationContext());
            }

            return true;
        }

        return true;
    }
}