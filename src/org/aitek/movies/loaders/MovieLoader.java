package org.aitek.movies.loaders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.aitek.movies.activities.MainActivity;
import org.aitek.movies.core.Movie;
import org.aitek.movies.core.MoviesManager;
import org.aitek.movies.utils.Constants;
import org.aitek.movies.utils.Logger;
import org.aitek.movies.utils.Mede8erCommander;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andrea
 * Date: 8/24/13
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * The MovieLoader class is responsible for loading the movies info from the datafile.
 */
public class MovieLoader extends GenericProgressIndicator {
    BitmapFactory.Options options;
    private Activity activity;
    private List<String> genres;
    private BufferedReader bufferedReader;
    private int fileLength;
    private int read = 0;
    private Mede8erCommander mede8erCommander;

    public MovieLoader(Activity activity) throws Exception{
        super(activity);
        this.activity = activity;
        mede8erCommander = Mede8erCommander.getInstance(activity);
    }

    @Override
    public void setup() throws Exception {

        options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        FileInputStream in = activity.openFileInput(Constants.MOVIES_FILE);
        fileLength = in.available();
        Logger.log("fileLength=" + fileLength);
        InputStreamReader inputStreamReader = new InputStreamReader(in);

        bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        Logger.log("line=" + line);
        genres = Arrays.asList(line.split(", "));
        mede8erCommander.getMoviesManager().setMovieGenres(genres);
    }

    @Override
    public int next() throws Exception {

        String line = bufferedReader.readLine();

        if (line != null) {
            String[] movieLine = line.split("\\|\\|");
            String title = movieLine[0] != null ? movieLine[0] : "NO TITLE";
            String filePath = movieLine[1];
            String movieGenres = movieLine[2];
            String persons = movieLine[3];

            URL url = new URL("file://" + filePath + "/folder.jpg");
            InputStream inputStream = (InputStream) url.getContent();
            Bitmap thumbnail = BitmapFactory.decodeStream(inputStream, null, options);
            Movie movie = new Movie(filePath, title, thumbnail, movieGenres, persons);
            mede8erCommander.getMoviesManager().insertMovie(movie);
            read += line.length();
        }
        else {
            read = fileLength;
        }
        return (int) (100 * ((double) read / fileLength));
    }

    @Override
    public void finish() throws Exception {
        bufferedReader.close();
        mede8erCommander.getMoviesManager().sortMovies();
        mede8erCommander.getMoviesManager().sortMovieGenres();
        MainActivity.imageAdapter.notifyDataSetChanged();
    }
}