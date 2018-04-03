package android.workshop.dmii.playlistspotifygenerator.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Artist;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benja on 02/04/2018.
 */

public class CustomArtistAdapter extends ArrayAdapter<Artist> implements AdapterView.OnItemSelectedListener{

    private Context context;
    private ArrayList<Artist> artistsList = new ArrayList<Artist>();
    private Artist currentArtist;

    public CustomArtistAdapter(Context context, int textViewResourceId, ArrayList<Artist> artists) {
        super(context, textViewResourceId);
        this.context = context;
        this.artistsList = artists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_list_row_artist, parent,false);

        Artist currentSong = artistsList.get(position);

        TextView release = (TextView) listItem.findViewById(R.id.artist_name);
        release.setText(currentSong.getName());

        return listItem;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentArtist = (Artist) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
