package android.workshop.dmii.playlistspotifygenerator.models;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import java.util.ArrayList;
import java.util.LinkedList;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.SnapshotId;
import kaaes.spotify.webapi.android.models.TrackToRemove;
import kaaes.spotify.webapi.android.models.TracksToRemove;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by benja on 21/02/2018.
 */

public class Playlist extends ViewModel {


    //define callback interface
    interface PlayListListener {
        void onTracksLoaded(ArrayList<Music> musicListTemp);
    }
    public interface PlayListRemoveListener {
        void onRemoveDone(boolean success, String error);
    }
    public interface PlayListDeleteListener {
        void onTrackDeleted(boolean success);
    }

    private String id;
    private ArrayList<Music> musicList;
    private String name;
    private String imageUrl;
    private SpotifyService spotify;


    public Playlist(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        spotify = SpotifyApiWrapper.getInstance().getService();
    }

    public void remove(PlayListRemoveListener listener) {
        // TODO
        spotify.unfollowPlaylist(User.getInstance().getId(), this.getId(), new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                listener.onRemoveDone(true, "");
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onRemoveDone(true, error.getMessage());

            }
        });
    }

    public void loadMusics(User.UserListeners listeners){
        getTracksFromPlayList(this.id, new PlayListListener() {
            @Override
            public void onTracksLoaded(ArrayList<Music> musicListTemp) {
                musicList = musicListTemp;
                listeners.onPlayListReady();
            }
        });
    }

    public ArrayList<Artist> getArtist(){

        ArrayList<Artist> artists = new ArrayList<Artist>();

        for (Music aMusic: musicList){
            for (Artist anArtist: aMusic.getArtist()){
                artists.add(anArtist);
            }
        }

        return artists;
    }

    /*public ArrayList<Music> convertTracks(ArrayList tracks) {

        ArrayList<Music> musicListTemp = new ArrayList<Music>();

        for (Track currentMusic : tracks){
            Music m = new Music(currentMusic.id);
            musicListTemp.add(m);
        }
    }*/

    public String getId() {
        return id;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    public void create(){}

    public ArrayList<String> read(int id){return null;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(){}

    public void delete(){}

    public void save(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public void deleteTrackFromPlayList(String uri, PlayListDeleteListener callback ){

        TrackToRemove trackToRemove = new TrackToRemove();
        trackToRemove.uri = uri;

        TracksToRemove tracksToRemove = new TracksToRemove();
        tracksToRemove.tracks = new LinkedList<TrackToRemove>();
        tracksToRemove.tracks.add(trackToRemove);

        spotify.removeTracksFromPlaylist(User.getInstance().getId(), this.getId(), tracksToRemove, new Callback<SnapshotId>() {
            @Override
            public void success(SnapshotId snapshotId, Response response) {
                callback.onTrackDeleted(true);
            }

            @Override
            public void failure(RetrofitError error) {
               callback.onTrackDeleted(false);
            }
        });
    }

    private void getTracksFromPlayList(String playListId, PlayListListener callBack){

        ArrayList<Music> musicListTemp  = new ArrayList<Music>();

        spotify.getPlaylistTracks(User.getInstance().getId(), playListId, new Callback<Pager<PlaylistTrack>>() {
            @Override
            public void success(Pager<PlaylistTrack> playlistTrackPager, Response response) {

                ArrayList<Music> musicListTemp  = new ArrayList<Music>();

                for (PlaylistTrack aTrack : playlistTrackPager.items){
                    musicListTemp.add(new Music(
                        aTrack.track.id,
                        aTrack.track.name,
                        aTrack.track.artists,
                        aTrack.track.album.name,
                        aTrack.track.preview_url,
                        (int) aTrack.track.duration_ms,
                        aTrack.track.uri)
                    );
                }

                callBack.onTracksLoaded(musicListTemp);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("Playslist", error.toString());

                callBack.onTracksLoaded(musicListTemp);
            }
        });
    }

}
