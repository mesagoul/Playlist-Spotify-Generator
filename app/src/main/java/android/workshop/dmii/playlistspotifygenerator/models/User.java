package android.workshop.dmii.playlistspotifygenerator.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by admin on 21/02/2018.
 */

public class User extends ViewModel{

    private static volatile User sInstance;

    private String id;
    private String name;
    private String login;
    private String token;
    private MutableLiveData<ArrayList<Playlist>> playListList = new MutableLiveData<>();
    private ArrayList<Music> MusicList;
    private SpotifyService spotify;

    public User() {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance()");
        }
        spotify = SpotifyApiWrapper.getInstance().getService();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public LiveData<ArrayList<Playlist>> getPlayListList() {
        return playListList;
    }

    public ArrayList<Music> getMusicList() {
        return MusicList;
    }
    public void setMusicList(ArrayList<Music> musicList) {
        MusicList = musicList;
    }


    public void connect(){
        // do something ...
    }

    public void init(){
        this.loadUser();
        this.loadPlayLists();
        this.loadMusics();
    }

    private void loadUser(){

        spotify.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, Response response) {
                Log.d("USER","Eror while trying to get User");
                id = userPrivate.id;
                name = userPrivate.display_name;
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("USER","Eror while trying to get User");
            }
        });

    }

    private void loadMusics(){

    }

    private void loadPlayLists(){

        Map<String, Object> options = new HashMap<>();
        options.put("limit", "30");

        spotify.getMyPlaylists(options, new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("PLAYLIST",spotifyError.toString());
            }

            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, Response response) {

                ArrayList<Playlist> playListListTemp = new ArrayList<Playlist>();

                // TODO Il faut mêttre tout dans le fragment pour pouvoir utiliser le livaData car il requiert l'utilisation d'un fragment dans le .of(this) Bisou
                /*viewModel = ViewModelProviders.of(this).get(User.class);
                viewModel.init();
                viewModel.getPlayListList().observe(this, playListList -> {
                    Log.d("PlayList", playListList.toString());
                });*/

                for (PlaylistSimple aPlayList : playlistSimplePager.items){
                    Playlist p = new Playlist(aPlayList.id, aPlayList.name);
                    playListListTemp.add(p);
                }

               playListList.setValue(playListListTemp);

            }
        });
    }
}
