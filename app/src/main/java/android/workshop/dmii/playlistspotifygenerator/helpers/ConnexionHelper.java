package android.workshop.dmii.playlistspotifygenerator.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class ConnexionHelper extends Activity {


    private static final String AUTH = "AUTH";
    private static final String TOKEN = "TOKEN";


    public static void onGetUserFinished(boolean success, Activity activity, Intent intent) {
        if(success){
            activity.startActivity(intent);
            activity.finish();
        } else {
            Log.d("USER","Error while trying to get User");
        }
    }

    public static void onSpotifyConnect(AuthenticationResponse response, Activity activity) {

        SharedPreferences sharedPreferences = activity.getBaseContext().getSharedPreferences(AUTH, MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putString(TOKEN, response.getAccessToken())
                .apply();

        // SET TO SPOTIFY API WRAPPER FOR SESSION
        SpotifyApiWrapper.getInstance().setToken(response.getAccessToken());
    }
}
