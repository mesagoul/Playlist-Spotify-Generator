package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.adapters.MusiclistAdapter;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import java.util.ArrayList;

public class PlaylistDetailTracksFragment extends Fragment implements MusiclistAdapter.MusiclistAdapterListener {

    public ArrayList<Music> dataListMusic;
    private RecyclerView uiListMusic;

    public PlaylistDetailTracksFragment() {
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

        MusiclistAdapter musiclistAdapter = new MusiclistAdapter(dataListMusic);
        uiListMusic.setAdapter(musiclistAdapter);
        musiclistAdapter.setListener(this);



    }

    @Override
    public void onItemClick(int position) {
        Log.d("ITEM",dataListMusic.get(position).getName());
    }
}
