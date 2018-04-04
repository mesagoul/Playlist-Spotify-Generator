package android.workshop.dmii.playlistspotifygenerator.models;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Track;

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
    private ArrayList<Artist> artistsList;
    private String albumName;
    private String uri;


    public Music(String id, String name, List<ArtistSimple> artists, String albumName, String url, Integer duration, String uri) {
        this.id = id;
        this.name = name;
        this.artistsList = convertArtists(artists);
        this.albumName = albumName;
        this.url = url;
        this.duration = duration;
        this.uri = uri;

    }

    public ArrayList<String> getArtistsNames(){

        ArrayList<String> tempList = new ArrayList<String>();

        for(Artist anArtist: this.artistsList){
            tempList.add(anArtist.getName());
        }

        return tempList;
    }

    public String getUrl(){
        return this.url;
    }

    public String getUri(){
        return this.uri;
    }

    public ArrayList<Artist> convertArtists(List<ArtistSimple> list){
        ArrayList<Artist> listTemp = new ArrayList<Artist>();

        for(ArtistSimple anArtist : list){
            listTemp.add(new Artist(
                    anArtist.id,
                    anArtist.name
            ));
        }
        return listTemp;
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

    public ArrayList<Artist> getArtist() {
        return artistsList;
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

    public String getImage() {
        return "";
    }

    public String getAlbum() {
        return albumName;
    }
}
