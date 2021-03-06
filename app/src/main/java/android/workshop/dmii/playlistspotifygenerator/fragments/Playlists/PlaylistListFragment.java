package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.adapters.ImageAdapter;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail.PlaylistDetailFragment;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail.PlaylistDetailTracksFragment;
import android.workshop.dmii.playlistspotifygenerator.models.Playlist;
import android.workshop.dmii.playlistspotifygenerator.models.User;

import java.util.ArrayList;

/**
 * Created by benja on 21/02/2018.
 */

public class PlaylistListFragment  extends Fragment{

    private GridView playlistGrid;
    private User user;

    public PlaylistListFragment(){
        //empty constructor
        user =  User.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_playlist, container,false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        playlistGrid = (GridView) view.findViewById(R.id.playlistList);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user = ViewModelProviders.of(this).get(User.getInstance().getClass());
        user.loadPlayLists();
        user.getPlayListList().observe(this, playListList -> {
            createGridView(playListList);
            User.getInstance().setPlayListListStatic(playListList);
        });

    }

    public void createGridView(ArrayList<Playlist> playListList){
        playlistGrid.setAdapter(new ImageAdapter(this.getContext(), playListList));

        playlistGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PlaylistDetailFragment playlistDetailFragment = new PlaylistDetailFragment();
                playlistDetailFragment.currentPlaylist = playListList.get(position);
                ((DashboardActivity)getActivity()).loadNewFragment( playlistDetailFragment, false,true);

            }
        });
    }
}
