package android.workshop.dmii.playlistspotifygenerator.adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Playlist;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by admin on 06/03/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
    };

    private ArrayList<Playlist> playlistToDisplay;

    public ImageAdapter(Context c,ArrayList<Playlist> playlists) {
        mContext = c;
        playlistToDisplay = playlists;
    }

    public int getCount() {
        //return mThumbIds.length;
        int total = playlistToDisplay.size();
        return total;
    }

    public Object getItem(int position) {
        return playlistToDisplay.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Playlist currentPlaylist = playlistToDisplay.get(position);

        Glide.with(mContext)
            .load(currentPlaylist.getImageUrl())
            //.centerCrop()
            //.crossFade()
            .into(imageView);

        imageView.setImageResource(mThumbIds[0]);























        return imageView;
    }
}
