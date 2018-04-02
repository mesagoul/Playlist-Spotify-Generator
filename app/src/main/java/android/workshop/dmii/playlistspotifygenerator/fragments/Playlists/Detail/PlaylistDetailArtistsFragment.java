package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.adapters.ArtistListAdapter;
import android.workshop.dmii.playlistspotifygenerator.adapters.MusiclistAdapter;
import android.workshop.dmii.playlistspotifygenerator.models.Artist;
import android.workshop.dmii.playlistspotifygenerator.models.Music;
import android.workshop.dmii.playlistspotifygenerator.models.Playlist;

import java.util.ArrayList;

public class PlaylistDetailArtistsFragment extends Fragment {

    public ArrayList<Artist> listArtist;
    private RecyclerView uiListMusic;
    public Playlist currentPlayList;

    public PlaylistDetailArtistsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_playlist_detail_tracks, container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiListMusic = view.findViewById(R.id.playlist_detail_music_list);
        uiListMusic.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ArtistListAdapter artistListAdapter = new ArtistListAdapter(listArtist);
        uiListMusic.setAdapter(artistListAdapter);

    }
}
