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

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import com.bumptech.glide.Glide;

public class MusicDetailFragment extends Fragment {

    public Music currentMusic;

    private ImageView songImage;
    private TextView songTextView;
    private TextView durationTextView;
    private TextView albumTextView;
    private ListView artistsList;

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
        return inflater.inflate(R.layout.fragment_music_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        songTextView = (TextView) getView().findViewById(R.id.music_detail_song_name);
        artistsList = (ListView) getView().findViewById(R.id.music_detail_artist_name);
        albumTextView = (TextView) getView().findViewById(R.id.music_detail_album_name);
        durationTextView = (TextView) getView().findViewById(R.id.music_detail_duration);

        songTextView.setText("Titre : " + currentMusic.getName());
        albumTextView.setText("Album : " + currentMusic.getAlbum());
        durationTextView.setText("Durée : " + String.valueOf(currentMusic.getDuration() / 1000));

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, currentMusic.getArtistsNames());
        artistsList.setAdapter(adapter);


    }
}
