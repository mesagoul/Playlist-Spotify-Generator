package android.workshop.dmii.playlistspotifygenerator.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by benja on 21/02/2018.
 */

public class Playlist {
    private String id;
    private ArrayList<Music> musicList;
    private String name;

    public Playlist(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Music> getMusicList() {
        return musicList;
    }
    public void setMusicList(ArrayList<Music> musicList) {
        this.musicList = musicList;
    }
    public void create(){

    }
    public ArrayList<String> read(int id){

        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void update(){

    }
    public void delete(){

    }
    public void save(){

    }
}
