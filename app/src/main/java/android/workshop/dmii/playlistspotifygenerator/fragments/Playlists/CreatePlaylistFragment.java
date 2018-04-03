package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists;

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

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.adapters.MusiclistAdapter;
import android.workshop.dmii.playlistspotifygenerator.models.Artist;
import android.workshop.dmii.playlistspotifygenerator.models.Music;
import android.workshop.dmii.playlistspotifygenerator.models.User;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.SavedTrack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CreatePlaylistFragment extends Fragment implements MusiclistAdapter.MusiclistAdapterListener{

    private User user;
    private RecyclerView uiListView;
    private SpotifyService spotify;
    private ArrayList<Integer> listPositionsChecked;
    private Button btnCreate;
    private EditText nameList;
    private ArrayList<Music> dataTracks;

    public CreatePlaylistFragment() {
        user = User.getInstance();
        spotify = SpotifyApiWrapper.getInstance().getService();
        listPositionsChecked = new ArrayList<Integer>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_create_playlist, container,false);
        return v;}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCreate = view.findViewById(R.id.create_playslist_btn);
        nameList = view.findViewById(R.id.create_playlist_name);

        uiListView = view.findViewById(R.id.create_playlist_list);
        uiListView.setLayoutManager(new LinearLayoutManager(getContext()));


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameList.getText().toString();
                createPlaylist(name);
            }
        });

        getSavedTracks();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void createPlaylist(String name){

        String userId = user.getId();

        Map<String, Object> options = new HashMap<>();
        options.put("name", name);
        options.put("public", true);

        spotify.createPlaylist(userId, options, new Callback<Playlist>() {
            @Override
            public void success(Playlist playlist, Response response) {
                if(listPositionsChecked.size() > 0){
                    saveTracksIntoPlayList(playlist.id);

                }else{
                    ((DashboardActivity)getActivity()).loadNewFragment(new PlaylistListFragment(),false, true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(),"Fail create PlayList",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTracksIntoPlayList(String playListId){

        String uri = "";

        for(int i = 0 ; i < listPositionsChecked.size() ; i++){
            int position = listPositionsChecked.get(i);
            String anUri = dataTracks.get(position).getUri();
            uri  = uri.concat(anUri);
            if(i != listPositionsChecked.size() -1){
                uri = uri.concat(",");
            }
        }

        Map<String, Object> optionParameters = new HashMap<>();
        Map<String, Object> optionBody = new HashMap<>();
        optionParameters.put("uris", uri);

        spotify.addTracksToPlaylist(user.getId(), playListId, optionParameters, optionBody, new Callback<Pager<PlaylistTrack>>() {
            @Override
            public void success(Pager<PlaylistTrack> playlistTrackPager, Response response) {
                ((DashboardActivity)getActivity()).loadNewFragment(new PlaylistListFragment(),false, true);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Fail Add Music to playlist", Toast.LENGTH_SHORT ).show();
            }
        });

    }

    private void getSavedTracks(){

        User.getInstance().getSavedTracks(new User.SavedMusicsListener() {
            @Override
            public void onSavedMusicsReady(ArrayList<Music> tempList) {
                dataTracks = tempList;
                onTracksReady(tempList);
            }
        });
    }

    private void onTracksReady(ArrayList<Music> tempList){
        MusiclistAdapter musiclistAdapter = new MusiclistAdapter(tempList, true);
        uiListView.setAdapter(musiclistAdapter);
        musiclistAdapter.setListener(this);
    }

    @Override
    public void onListenClick(int position) {}

    @Override
    public void onCrossClick(int position, LinearLayout music_container, ArrayList<Music> listMusic) {
        if(listPositionsChecked.contains(position)){

            int posItem = listPositionsChecked.indexOf(position);
            listPositionsChecked.remove(posItem);

            music_container.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }else{
            listPositionsChecked.add(position);
            music_container.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
