package android.workshop.dmii.playlistspotifygenerator.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.helpers.ConnexionHelper;
import android.workshop.dmii.playlistspotifygenerator.models.User;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LogInActivity extends AppCompatActivity{

    private static final String CLIENT_ID = "c4636ffdc7844503ba3e89d7c4908d66";
    private static final String REDIRECT_URI = "dmii18://callback";
    private static final int REQUEST_CODE = 1001;
    private Intent toMainActivity;
    private AuthenticationRequest.Builder builder;
    private AuthenticationRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loadAuthClient();

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button connectionButton = (Button) findViewById(R.id.activity_login_button);
        toMainActivity = new Intent(LogInActivity.this, DashboardActivity.class);

        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationClient.openLoginActivity(LogInActivity.this, REQUEST_CODE, request);
            }
        });
    }

    public void loadAuthClient(){

       builder  = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
       builder.setScopes(new String[]{"streaming", "user-library-read", "playlist-modify-public"});
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
                        ConnexionHelper.onGetUserFinished(true, LogInActivity.this, toMainActivity);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ConnexionHelper.onGetUserFinished(false, LogInActivity.this, toMainActivity);
                    }
                });
            }
        }
    }
}