package android.workshop.dmii.playlistspotifygenerator.models;

import java.util.ArrayList;

/**
 * Created by admin on 21/02/2018.
 */

public class Artist {

    private String id;
    private String name;
    private ArrayList<Album> albumList;
    private Integer listOfMusic;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
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
    public ArrayList<Album> getAlbumList() {
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
