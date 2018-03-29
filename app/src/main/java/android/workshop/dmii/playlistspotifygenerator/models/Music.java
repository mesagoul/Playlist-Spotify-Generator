package android.workshop.dmii.playlistspotifygenerator.models;

import java.util.ArrayList;

/**
 * Created by benja on 21/02/2018.
 */

public class Music {

    private String id;
    private String name;
    private Integer duration;
    private Integer score;
    private String url;
    private ArrayList<Genre> genreList;
    private String artist;
    private String albumName;


    public Music(String id, String name, String artist, String albumName, String url, Integer duration) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.albumName = albumName;
        this.url = url;
        this.duration = duration;

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

    public float getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ArrayList<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<Genre> genreList) {
        this.genreList = genreList;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public void play(){

    }

    public void pause(){

    }

    public void stop(){

    }

    public void playAt(Float time){

    }

    public void saveMusic(){

    }

    public void saveMusicIntoPlaylist(Integer id){

    }

    public Boolean share(){

        return true;
    }

    public void remove(){

    }

    public void removeFromPlaylist(Integer playlistId){

    }
}
