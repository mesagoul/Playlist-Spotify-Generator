package android.workshop.dmii.playlistspotifygenerator.models;

import java.lang.reflect.Array;

/**
 * Created by admin on 21/02/2018.
 */

public class User {

    private Integer id;
    private String name;
    private String login;
    private String token;
    private ArrayList<PlayList> playListList;
    private ArrayList<Music> MusicList;

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public ArrayList<PlayList> getPlayListList() {
        return playListList;
    }
    public void setPlayListList(ArrayList<PlayList> playListList) {
        this.playListList = playListList;
    }
    public ArrayList<Music> getMusicList() {
        return MusicList;
    }
    public void setMusicList(ArrayList<Music> musicList) {
        MusicList = musicList;
    }


    public void connect(){
        // do something ...
    }
}
