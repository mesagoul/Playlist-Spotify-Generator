package android.workshop.dmii.playlistspotifygenerator.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.helpers.ConnexionHelper;
import android.workshop.dmii.playlistspotifygenerator.models.User;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by benja on 06/03/2018.
 */

public class SplashScreen extends Activity{

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
        builder.setScopes(new String[]{"streaming", "user-library-read", "playlist-modify-public", "playlist-modify-public"});
        request = builder.build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {

            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                // Connect to spotify
                ConnexionHelper.onSpotifyConnect(response, this);


                // Get ser informations
                User.getInstance().spotify.getMe(new Callback<UserPrivate>() {
                    @Override
                    public void success(UserPrivate userPrivate, Response response) {
                        User.getInstance().setId(userPrivate.id);
                        User.getInstance().setName(userPrivate.display_name);

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                                .setRequestInterceptor(new RequestInterceptor() {
                                    @Override
                                    public void intercept(RequestFacade request) {
                                        //request.addHeader("Authorization", "Bearer " + token);
                                        request.addHeader("scope", "user-library-read");
                                    }
                                })
                                .build();
                        ConnexionHelper.onGetUserFinished(true, SplashScreen.this, toRefreshActivity);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ConnexionHelper.onGetUserFinished(false, SplashScreen.this, toRefreshActivity);
                    }
                });
            }
        }
    }
}
