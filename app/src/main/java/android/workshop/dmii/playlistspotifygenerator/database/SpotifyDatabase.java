package android.workshop.dmii.playlistspotifygenerator.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.workshop.dmii.playlistspotifygenerator.daos.ArtistEntityDao;
import android.workshop.dmii.playlistspotifygenerator.helpers.Converter;
import android.workshop.dmii.playlistspotifygenerator.models.ArtistEntity;

/**
 * Created by benja on 31/03/2018.
 */

@Database(entities = {ArtistEntity.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class SpotifyDatabase extends RoomDatabase {

    public abstract ArtistEntityDao artistEntityDao();

    private static SpotifyDatabase sInstance;

    public static SpotifyDatabase getDb(final Context context) {
        if (sInstance == null) {
            synchronized (SpotifyDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),SpotifyDatabase.class,"spotify_database")
                                .fallbackToDestructiveMigration()
                                .build();
                }
            }
        }

        return sInstance;
    }
}
