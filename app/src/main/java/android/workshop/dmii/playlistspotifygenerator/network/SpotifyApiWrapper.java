package android.workshop.dmii.playlistspotifygenerator.network;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by admin on 06/03/2018.
 */

public class SpotifyApiWrapper {

    private static volatile SpotifyApiWrapper sInstance;
    private final SpotifyApi api;

    private SpotifyApiWrapper() {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance()");
        }
        api = new SpotifyApi();
    }

    public static SpotifyApiWrapper getInstance() {
        if (sInstance == null) {
            synchronized (SpotifyApiWrapper.class) {
                if (sInstance == null) sInstance = new SpotifyApiWrapper();
            }
        }

        return sInstance;
    }

    public void setToken(String token) {
        api.setAccessToken(token);
    }

    public SpotifyService getService() {
        return api.getService();
    }
}