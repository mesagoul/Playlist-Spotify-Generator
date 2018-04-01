package android.workshop.dmii.playlistspotifygenerator.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.workshop.dmii.playlistspotifygenerator.helpers.Converter;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by admin on 21/02/2018.
 */

@Entity(tableName = "artist_table")
@TypeConverters({Converter.class})
public class ArtistEntity {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private List<Album> albumList;
    private Integer listOfMusic;

    public ArtistEntity(){}

    public ArtistEntity(Artist artist) {
        setId(artist.id);
        setName(artist.name);
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Album> getAlbumList() {
        return albumList;
    }
    public void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }
    public Integer getListOfMusic() {
        return listOfMusic;
    }
    public void setListOfMusic(Integer listOfMusic) {
        this.listOfMusic = listOfMusic;
    }
}
