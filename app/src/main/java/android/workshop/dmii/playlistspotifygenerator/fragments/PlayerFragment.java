package android.workshop.dmii.playlistspotifygenerator.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.User;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Recommendations;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by benja on 21/02/2018.
 */

public class PlayerFragment extends Fragment {

    private Button btn_prev;
    private Button btn_play;
    private Button btn_next;
    private SpotifyService spotify;
    private User user;

    public  PlayerFragment(){
        // empty constructor;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_player, container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = User.getInstance();
        spotify = SpotifyApiWrapper.getInstance().getService();

        btn_next = view.findViewById(R.id.player_next_btn);
        btn_play = view.findViewById(R.id.player_play_btn);
        btn_prev = view.findViewById(R.id.player_prev_btn);

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Prev", Toast.LENGTH_SHORT).show();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Play", Toast.LENGTH_SHORT).show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
