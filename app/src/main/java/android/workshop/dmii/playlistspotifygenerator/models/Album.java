package android.workshop.dmii.playlistspotifygenerator.models;

import java.util.ArrayList;

/**
 * Created by admin on 21/02/2018.
 */

public class Album {

    private Integer id;
    private String imageUrl;
    private ArrayList<Music> musicList;

    public Album(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public ArrayList<Music> getMusicList() {
        return musicList;
    }
    public void setMusicList(ArrayList<Music> musicList) {
        this.musicList = musicList;
    }
}
