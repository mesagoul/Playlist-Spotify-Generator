package android.workshop.dmii.playlistspotifygenerator.models;

import java.util.ArrayList;

/**
 * Created by benja on 21/02/2018.
 */

public class Music {
    private Integer id;
    private String title;
    private Float duration;
    private Integer score;
    private ArrayList<Genre> genreList;
    private String artist;


    public Music(Integer id) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
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
