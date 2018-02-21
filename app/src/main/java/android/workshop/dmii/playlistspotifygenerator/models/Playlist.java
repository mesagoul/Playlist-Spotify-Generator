package android.workshop.dmii.playlistspotifygenerator.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by benja on 21/02/2018.
 */

public class Playlist {
    private int id;
    private ArrayList<Music> musicList;

    public Playlist(int id) {
        this.id = id;
    }

    public int getId() {
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

    public void update(){

    }

    public void delete(){

    }

    public void save(){

    }
}
