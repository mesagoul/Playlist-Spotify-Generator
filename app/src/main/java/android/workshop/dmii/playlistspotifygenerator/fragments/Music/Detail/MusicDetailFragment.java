package android.workshop.dmii.playlistspotifygenerator.fragments.Music.Detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

public class MusicDetailFragment extends Fragment {

    public Music currentMusic;

    public MusicDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //TODO in the method were we call THIS fragment :
        /*
        * Fragment detailFragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("artist", artist.getName());
            bundle.putString("music", music.getName());
            bundle.putString("image", music.getImage());
            detailFragment.setArguments(bundle);
            ou via un intent.putExtra
        * */

        return inflater.inflate(R.layout.fragment_music_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView songImage = (ImageView) getView().findViewById(R.id.music_image);
        TextView songTextView = (TextView) getView().findViewById(R.id.music_detail_song_name);
        TextView artistTextView = (TextView) getView().findViewById(R.id.music_detail_artist_name);

        Bundle bundle = getArguments();

        String artist = bundle.getString("artist") ;
        artistTextView.setText(artist);

        String song = bundle.getString("music");
        songTextView.setText(song);


    }
}
