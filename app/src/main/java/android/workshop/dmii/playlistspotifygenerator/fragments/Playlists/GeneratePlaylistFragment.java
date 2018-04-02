package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.workshop.dmii.playlistspotifygenerator.models.User;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.client.Response;

public class GeneratePlaylistFragment extends Fragment {

    private User user;
    private SpotifyService spotify;
    private ArrayList<String> categories;
    private String selectedMusicCategory;
    private String newPlaylsitName;
    private String newArtistName;
    private String newSongName;

    public GeneratePlaylistFragment() {
        user = User.getInstance();
        spotify = SpotifyApiWrapper.getInstance().getService();
        selectedMusicCategory = "";
        newPlaylsitName = "";
        newArtistName = "";
        newSongName = "";
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

        Spinner spinner = (Spinner) getView().findViewById(R.id.genre_list);
        Button generatePlaylistBtn = (Button) getView().findViewById(R.id.generate_btn);
        TextView playlistNameView = (TextView) getView().findViewById(R.id.playlist_name);
        TextView artistNameView = (TextView) getView().findViewById(R.id.artist_name_text);
        TextView songNameView = (TextView) getView().findViewById(R.id.songname_text);


        Map<String, Object> options = new HashMap<>();
        options.put("limit", 50);

        spotify.getCategories(options, new SpotifyCallback<CategoriesPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("GENERATE_FRAG",spotifyError.toString());
            }

            @Override
            public void success(CategoriesPager categoriesPager, Response response) {

                //setting spinner items
                categories = new ArrayList<String>();

                for(int i =0; i<categoriesPager.categories.total ; i++){
                    String currentCategory = (String) categoriesPager.categories.items.get(i).name;
                    categories.add(currentCategory);
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMusicCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        playlistNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPlaylsitName = playlistNameView.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });


        artistNameView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newArtistName = artistNameView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        songNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newSongName = songNameView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        generatePlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if playlist name is null
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
    }
}
