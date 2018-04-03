package android.workshop.dmii.playlistspotifygenerator.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import java.util.ArrayList;

/**
 * Created by benja on 02/04/2018.
 */

public class CustomMusicAdapter extends ArrayAdapter<Music> implements AdapterView.OnItemSelectedListener{
    private Context context;
    private ArrayList<Music> tracks = new ArrayList<Music>();
    private Music currentMusic;

    public CustomMusicAdapter(Context context, int textViewResourceId, ArrayList<Music> musics) {
        super(context, textViewResourceId);
        this.context = context;
        this.tracks = musics;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentMusic = (Music) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_list_row_music, parent,false);

        Music currentSong = tracks.get(position);

        TextView release = (TextView) listItem.findViewById(R.id.music_name);
        release.setText(currentSong.getName());

        return listItem;
    }
}
