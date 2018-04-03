package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.helpers.CustomAdapter;
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
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.Recommendations;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GeneratePlaylistFragment extends Fragment {

    private User user;
    private SpotifyService spotify;
    private ArrayList<String> categories;

    private String newPlaylistName;

    private String selectedMusicCategory;
    private Artist selectedArtist;
    private Music selectedTrack;

    private Spinner artistsSpinner;
    private Spinner songsSpinner;
    private Spinner categoriesSpinner;

    public GeneratePlaylistFragment() {
        user = User.getInstance();
        spotify = SpotifyApiWrapper.getInstance().getService();

        newPlaylistName = "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generate_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button generatePlaylistBtn = (Button) getView().findViewById(R.id.generate_btn);
        TextView playlistNameView = (TextView) getView().findViewById(R.id.playlist_name);

        categoriesSpinner = (Spinner) getView().findViewById(R.id.genre_list);
        artistsSpinner = (Spinner) getView().findViewById(R.id.artists_list);
        songsSpinner = (Spinner) getView().findViewById(R.id.tracks_list);


        Map<String, Object> options = new HashMap<>();
        options.put("limit", 50);

        spotify.getCategories(options, new SpotifyCallback<CategoriesPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("GENERATE_FRAG",spotifyError.toString());
            }

            @Override
            public void success(CategoriesPager categoriesPager, Response response) {

                //setting categoriesSpinner items and adapter
                categories = new ArrayList<String>();

                for(int i =0; i<categoriesPager.categories.total ; i++){
                    String currentCategory = (String) categoriesPager.categories.items.get(i).name;
                    categories.add(currentCategory);
                }

                ArrayAdapter<String> categoriesDataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
                categoriesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriesSpinner.setAdapter(categoriesDataAdapter);


                //setting artistsSpinner items and adapter
                user.getAllArtistsAndMusics(new User.GetAllListeners() {
                    @Override
                    public void onAllReady(ArrayList<Artist> listArtists, ArrayList<Music> listMusics) {
                        loadSpinners(artistsSpinner, songsSpinner, listArtists, listMusics);
                    }
                });
            }

        });

        generatePlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if playlist name is null
                newPlaylistName = playlistNameView.getText().toString();


                if(!playlistNameView.getText().toString().equals("")){
                    generatePlaylist();
                }else{
                    Toast.makeText(getContext(), "Set a name to your playlist ! :)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void generatePlaylist(){
        Toast.makeText(getContext(), "generating playlist ...", Toast.LENGTH_SHORT).show();

        Map<String, Object> options = new HashMap<>();
        String userId = user.getId();

        options.put("name", newPlaylistName);
        options.put("public", true);
        options.put("limit", 15);

        if(!TextUtils.isEmpty(selectedMusicCategory)){
            options.put("seed_genres", selectedMusicCategory);
        }

        if(!TextUtils.isEmpty(selectedArtist.getName())){
            options.put("seed_artist", selectedArtist.getId());
        }

        if(!TextUtils.isEmpty(selectedTrack.getName())){
            options.put("seed_tracks", selectedTrack.getId());
        }


        spotify.getRecommendations(options, new SpotifyCallback<Recommendations>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Toast.makeText(getContext(), spotifyError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(Recommendations recommendations, Response response) {
                createPlaylist(newPlaylistName, recommendations);
            }
        });


    }

    public void loadSpinners(Spinner artistsSpinner, Spinner tracksSpinner, ArrayList<Artist> listArtists, ArrayList<Music> listMusics){
        CustomAdapter artistAdapter = new CustomAdapter(getContext(), listArtists);
        artistsSpinner.setAdapter(artistAdapter);

        artistsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArtist = listArtists.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        CustomAdapter tracksAdapter = new CustomAdapter(getContext(), listMusics);
        tracksSpinner.setAdapter(tracksAdapter);

        tracksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTrack = listMusics.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMusicCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }

    public void createPlaylist(String name, Recommendations recommendations){
        String userId = user.getInstance().getId();

        Map<String, Object> options = new HashMap<>();
        options.put("name", name);
        options.put("public", true);


        spotify.createPlaylist(userId, options, new Callback<Playlist>() {
            @Override
            public void success(Playlist playlist, Response response) {
                saveTracksIntoPlayList(playlist.id, recommendations);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTracksIntoPlayList(String playListId, Recommendations recommendations){

        String uri = "";

        for(int i = 0 ; i < recommendations.tracks.size() ; i++){
            Track currentTrack = recommendations.tracks.get(i);

            uri  = uri.concat(currentTrack.uri);
            if(i != recommendations.tracks.size() -1){
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        });

    }
}
