package org.aitek.controller.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import org.aitek.controller.mede8er.Mede8erCommander;
import org.aitek.controller.utils.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: andrea
 * Date: 8/16/13
 * Time: 5:11 PM
 */
public class ImageAdapter extends BaseAdapter {

    private final Mede8erCommander mede8erCommander;
    private Context context;

    public ImageAdapter(Activity activity) {
        this.context = activity.getApplicationContext();
        this.mede8erCommander = Mede8erCommander.getInstance(context);
    }

    public int getCount() {
        return mede8erCommander.getMoviesManager().getCount();
    }

    @Override
    public Object getItem(int i) {
        return mede8erCommander.getMoviesManager().getMovie(i).getThumbnail();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(180, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(7, 7, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mede8erCommander.getMoviesManager().getMovie(position).getThumbnail());
        return imageView;
    }

}