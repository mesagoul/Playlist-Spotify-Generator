package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.ArtistEntity;

import java.util.ArrayList;

public class PlaylistDetailArtistsFragment extends Fragment {

    public ArrayList<ArtistEntity> listArtist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_playlist_detail_artists, container,false);
        return v;
    }
}
