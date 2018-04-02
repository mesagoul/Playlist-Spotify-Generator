package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.workshop.dmii.playlistspotifygenerator.models.User;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Playlist;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GeneratePlaylistFragment extends Fragment {

    private User user;
    private SpotifyService spotify;
    private ArrayList<String> categories;

    private String newPlaylistName;
    private String selectedMusicCategory;
    private String selectedArtist;
    private String selectedTrack;

    public GeneratePlaylistFragment() {
        user = User.getInstance();
        spotify = SpotifyApiWrapper.getInstance().getService();

        newPlaylistName = "";
        selectedMusicCategory = "";
        selectedArtist = "";
        selectedTrack = "";
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

        Spinner categoriesSpinner = (Spinner) getView().findViewById(R.id.genre_list);
        Spinner artistsSpinner = (Spinner) getView().findViewById(R.id.artists_list);
        Spinner tracksSpinner = (Spinner) getView().findViewById(R.id.tracks_list);


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

                ArrayAdapter<String> categoriesDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
                categoriesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriesSpinner.setAdapter(categoriesDataAdapter);


                //setting artistsSpinner items and adapter
                ArrayList<String> artists = new ArrayList<String>();
                artists.add("lol");
                artists.add("mdr");
                artists.add("xd");

                /*for(int i =0; i<artistsPager.artists.total ; i++){
                    String currentCategory = (String) artistsPager.artists.items.get(i).name;
                    categories.add(currentCategory);
                }*/

                ArrayAdapter<String> artistsDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, artists);
                artistsDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                artistsSpinner.setAdapter(artistsDataAdapter);


                //Setting tracks spinner
                ArrayList<String> tracks = new ArrayList<String>();
                tracks.add("musique 1");
                tracks.add("musique 2");
                tracks.add("musique 3");
                tracks.add("musique 4");
                tracks.add("musique 5");

                ArrayAdapter<String> tracksDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tracks);
                tracksDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tracksSpinner.setAdapter(tracksDataAdapter);
            }

        });

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMusicCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        artistsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArtist = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        tracksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTrack = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
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

        String userId = user.getId();
        Map<String, Object> options = new HashMap<>();
        options.put("name", newPlaylistName);
        options.put("public", true);

        spotify.createPlaylist(userId, options, new Callback<Playlist>() {
            @Override
            public void success(Playlist playlist, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
