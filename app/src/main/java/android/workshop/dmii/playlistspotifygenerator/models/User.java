package android.workshop.dmii.playlistspotifygenerator.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.SavedTrack;
import retrofit.client.Response;

/**
 * Created by admin on 21/02/2018.
 */

public class User extends ViewModel{

    interface UserListeners{
        void onPlayListReady();
    }

    public interface GetAllListeners{
        void onAllReady(ArrayList<Artist> listArtists, ArrayList<Music> listMusics);
    }

    public interface SavedMusicsListener{
        void onSavedMusicsReady(ArrayList<Music> tempList);

    }

    public static final User userInstance = new User();

    private String id;
    private String name;
    private String login;
    private String token;
    private MutableLiveData<ArrayList<Playlist>> playListList = new MutableLiveData<>();
    private ArrayList<Playlist> playListListStatic = new ArrayList<Playlist>();
    private ArrayList<Music> MusicList;
    public SpotifyService spotify;
    private ArrayList<Music> savedMusics = new ArrayList<Music>();

    public void setPlayListListStatic(ArrayList<Playlist> playListListStatic) {
        this.playListListStatic = playListListStatic;
    }

    public User() {
        spotify = SpotifyApiWrapper.getInstance().getService();
    }

    public static User getInstance() {
        return userInstance;
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


    public void getSavedTracks(SavedMusicsListener listener){
        ArrayList<Music> tempList = new ArrayList<Music>();
        Map<String, Object> options = new HashMap<>();
        options.put("limit", "50");

        spotify.getMySavedTracks(options, new SpotifyCallback<Pager<SavedTrack>>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                listener.onSavedMusicsReady(tempList);
            }

            @Override
            public void success(Pager<SavedTrack> savedTrackPager, Response response) {


                for(SavedTrack aTrack: savedTrackPager.items){
                    tempList.add(new Music(
                            aTrack.track.id,
                            aTrack.track.name,
                            aTrack.track.artists,
                            aTrack.track.album.name,
                            aTrack.track.preview_url,
                            (int) aTrack.track.duration_ms,
                            aTrack.track.uri
                    ));
                }

                savedMusics = tempList;
                listener.onSavedMusicsReady(tempList);

            }
        });
    }




    public void getAllArtistsAndMusics(GetAllListeners listeners){

        ArrayList<String> musicIds = new ArrayList<String>();
        ArrayList<String> artistsIds = new ArrayList<String>();

        ArrayList<Artist> listArtists = new ArrayList<Artist>();
        ArrayList<Music> listMusics = new ArrayList<Music>();

        // FROM PLAYLISTS
        for(Playlist aPlaylist: playListListStatic){

            // MUSICS
            for(Music aMusic: aPlaylist.getMusicList()){
                if(!musicIds.contains(aMusic.getId())){
                    musicIds.add(aMusic.getId());
                    listMusics.add(aMusic);
                }
            }

            // ARTISTS
            for(Artist anArtist: aPlaylist.getArtist()){

                if(!artistsIds.contains(anArtist.getId())){
                    artistsIds.add(anArtist.getId());
                    listArtists.add(anArtist);
                }
            }
        }

        // FROM SAVED MUSICS
        this.getSavedTracks(new SavedMusicsListener() {
            @Override
            public void onSavedMusicsReady(ArrayList<Music> list) {

                for (Music aMusic: list){
                    // ARTISTS
                    for(Artist anArtist :aMusic.getArtist()){
                        if(!artistsIds.contains(anArtist.getId())){
                            artistsIds.add(anArtist.getId());
                            listArtists.add(anArtist);
                        }
                    }
                    // MUSICS
                    if(!musicIds.contains(aMusic.getId())){
                        musicIds.add(aMusic.getId());
                        listMusics.add(aMusic);
                    }
                }

                listeners.onAllReady(listArtists, listMusics);
            }
        });
    }


    public void setId(String id) {
        this.id = id;
    }

    public void loadPlayLists(){

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

                for(int i = 0 ; i < playlistSimplePager.total ; i++){
                    final int cpt = i;
                    PlaylistSimple aPlayList = playlistSimplePager.items.get(i);

                    String image = (aPlayList.images.size() > 0 ) ? aPlayList.images.get(0).url  : "https://lh5.ggpht.com/UxhAlm7oD9I3NnuwymJ-2NrSD7IKbu4MvorTOnSeaDWi6-k3kFgCBXmLKULmuvs81w=h310";
                    Playlist p = new Playlist(aPlayList.id, aPlayList.name, image);

                    p.loadMusics(new UserListeners() {
                        @Override
                        public void onPlayListReady() {
                            playListListTemp.add(p);

                            if( cpt == playlistSimplePager.total -1){
                                playListList.setValue(playListListTemp);
                            }
                        }
                    });
                }
            }
        });
    }
}
