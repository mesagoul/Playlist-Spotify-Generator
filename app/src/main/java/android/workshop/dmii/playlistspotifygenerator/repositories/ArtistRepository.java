package android.workshop.dmii.playlistspotifygenerator.repositories;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.workshop.dmii.playlistspotifygenerator.daos.ArtistEntityDao;
import android.workshop.dmii.playlistspotifygenerator.database.SpotifyDatabase;
import android.workshop.dmii.playlistspotifygenerator.models.ArtistEntity;
import android.workshop.dmii.playlistspotifygenerator.network.SpotifyApiWrapper;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import retrofit.client.Response;

/**
 * Created by benja on 31/03/2018.
 */

public class ArtistRepository {
    private final ArtistEntityDao dao;
    private final SpotifyService api;

    ArtistRepository(Application application) {
        SpotifyDatabase db = SpotifyDatabase.getDb(application);
        dao = db.artistEntityDao();
        api = SpotifyApiWrapper.getInstance().getService();
    }

    public LiveData<ArtistEntity> getArtistById(String artistId) {
        fetchArtistEntity(artistId);
        return dao.load(artistId);
    }

    private void fetchArtistEntity(String artistId) {
        api.getArtist(artistId, new SpotifyCallback<Artist>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.e("Artist", spotifyError.getMessage());
            }

            @Override
            public void success(Artist artist, Response response) {
                Log.d("Artist", artist.name);
                new saveAsyncTask(dao).execute(new ArtistEntity(artist));
            }
        });
    }

    private static class saveAsyncTask extends AsyncTask<ArtistEntity, Void, Void> {
        private ArtistEntityDao asyncDao;

        saveAsyncTask(ArtistEntityDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(ArtistEntity... artistEntities) {
            asyncDao.save(artistEntities[0]);
            return null;
        }
    }
}
