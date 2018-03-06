package android.workshop.dmii.playlistspotifygenerator.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.workshop.dmii.playlistspotifygenerator.R;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.authentication.LoginActivity;

public class LogInActivity extends AppCompatActivity{

    private static final String CLIENT_ID = "c4636ffdc7844503ba3e89d7c4908d66";
    private static final int REQUEST_CODE = 1001;
    private static final String REDIRECT_URI = "dmii18://callback";
    private static final String AUTH = "AUTH";
    private static final String TOKEN = "TOKEN";
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button connectionButton = (Button) findViewById(R.id.activity_login_button);

        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(AUTH, MODE_PRIVATE);

        token = sharedPreferences.getString(TOKEN, null);
        if(token != null){
            Log.d(LoginActivity.class.getSimpleName(),token);
            Intent toMainActivity = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(toMainActivity);
            finish();
        }

        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationRequest.Builder builder =
                        new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

                builder.setScopes(new String[]{"streaming"});
                AuthenticationRequest request = builder.build();

                AuthenticationClient.openLoginActivity(LogInActivity.this, REQUEST_CODE, request);
            }
        });
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


                /*
                    GET RECOMMENDATIONS
                SpotifyApi api = new SpotifyApi();
                String token = response.getAccessToken();
                api.setAccessToken(token);

                SpotifyService spotify = api.getService();

                Map<String, Object> options = new HashMap<>();
                options.put("seed_genres", "electro");
                spotify.getRecommendations(options, new Callback<Recommendations>() {
                    @Override
                    public void success(Recommendations recommendations, Response response) {
                        Log.d("Recommendations success", String.valueOf(recommendations.tracks.size()));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Recommendations error", error.getMessage());
                    }
                });
                */
            }
        }
    }

}