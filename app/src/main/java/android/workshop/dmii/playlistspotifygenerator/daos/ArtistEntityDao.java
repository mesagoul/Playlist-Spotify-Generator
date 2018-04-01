package android.workshop.dmii.playlistspotifygenerator.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.workshop.dmii.playlistspotifygenerator.helpers.Converter;
import android.workshop.dmii.playlistspotifygenerator.models.ArtistEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by benja on 31/03/2018.
 */

@Dao
@TypeConverters({Converter.class})

public interface ArtistEntityDao {
    @Insert(onConflict = REPLACE)
    void save(ArtistEntity artistEntity);

    @Query("SELECT * FROM artist_table WHERE id = :artistId")
    LiveData<ArtistEntity> load(String artistId);

}
