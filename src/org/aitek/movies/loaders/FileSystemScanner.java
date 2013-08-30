package org.aitek.movies.loaders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.aitek.movies.core.MoviesManager;
import org.aitek.movies.core.Movie;
import org.aitek.movies.utils.Constants;
import org.aitek.movies.utils.Mede8erCommander;
import org.aitek.movies.utils.XmlParser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: andrea
 * Date: 8/16/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileSystemScanner extends GenericProgressIndicator {

    private File[] list;
    private int listCounter = 0;
    private int fileNumber;

    public FileSystemScanner(Activity activity) {
        super(activity);
    }

    @Override
    public void setup() {
        list = new File(Constants.ROOT_DIRECTORY).listFiles();
        fileNumber = list.length;
    }

    @Override
    public int next() throws Exception {

        saveMovieInfo(list[listCounter]);
        return (int) (100 * ((double) listCounter / fileNumber));
    }


    @Override
    public void finish() throws Exception {
        Mede8erCommander mede8erCommander = Mede8erCommander.getInstance(activity);
        mede8erCommander.getMoviesManager().saveMovies(activity);
        mede8erCommander.getMoviesManager().sortMovies();
        mede8erCommander.getMoviesManager().sortMovieGenres();
    }

    private void saveMovieInfo(File f) throws Exception {

        listCounter++;
        if (f.isDirectory()) {

            File imageFile = new File(f.getAbsoluteFile() + "/folder.jpg");
            File xmlFile = new File(Constants.ROOT_DIRECTORY + "/" + f.getName() + f.getName() + ".xml");
            if (imageFile.exists() && xmlFile.exists()) {

                String xmlFilename = "file://" + Constants.ROOT_DIRECTORY + "/" + f.getName() + f.getName() + ".xml";
                InputStream xmlInputStream = (InputStream) new URL(xmlFilename).getContent();
                Movie movie = XmlParser.parse(xmlInputStream, activity);
                movie.setAbsolutePath(f.getAbsolutePath());

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;

                String imageFilename = "file://" + f.getAbsoluteFile() + "/folder.jpg";
                InputStream inputStream = (InputStream) new URL(imageFilename).getContent();
                Bitmap thumbnail = BitmapFactory.decodeStream(inputStream, null, options);
                movie.setThumbnail(thumbnail);
            }
        }
    }
}