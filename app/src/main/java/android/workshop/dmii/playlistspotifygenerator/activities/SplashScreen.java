package android.workshop.dmii.playlistspotifygenerator.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

/**
 * Created by benja on 06/03/2018.
 */

public class SplashScreen extends Activity {

    private static final int SPLASH_TIME_OUT = 1500;
    private static final String CLIENT_ID = "c4636ffdc7844503ba3e89d7c4908d66";
    private static final String REDIRECT_URI = "dmii18://callback";
    private static final String AUTH = "AUTH";
    private static final String TOKEN = "TOKEN";
    private static final int REQUEST_CODE = 1001;
    private String token;

    private AuthenticationRequest.Builder builder;
    private AuthenticationRequest request;

    private Intent toRefreshActivity;
    private Intent toLogInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        // SET VIEW
        setContentView(R.layout.activity_splash);


        // DECLARE INTENTS
        toLogInActivity = new Intent(SplashScreen.this, LogInActivity.class);
        toRefreshActivity = new Intent(SplashScreen.this, DashboardActivity.class);



        // GET SHARED PREFERENCES
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(AUTH, MODE_PRIVATE);


        // GET TOKEN
        token = sharedPreferences.getString(TOKEN, null);

        // IF ALREADY logged, refresh token
        if(token != null){
            loadAuthClient();
            AuthenticationClient.openLoginActivity(SplashScreen.this, REQUEST_CODE, request);
        }else{
            // WAIT 1.5seconds before go to LogInActivity
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(toLogInActivity);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    public void loadAuthClient(){
        builder  = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        request = builder.build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(AUTH, MODE_PRIVATE);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                sharedPreferences
                        .edit()
                        .putString(TOKEN, response.getAccessToken())
                        .apply();

                // SET TO SPOTIFY API WRAPPER FOR SESSION
                SpotifyApiWrapper.getInstance().setToken(token);

                // GO TO MAIN ACTIVITY
                startActivity(toRefreshActivity);
                finish();
            }
        }
    }
}
