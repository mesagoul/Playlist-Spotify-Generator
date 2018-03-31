package android.workshop.dmii.playlistspotifygenerator.models;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

/**
 * Created by benja on 30/03/2018.
 */

public class UserWrapper {
    private static volatile UserWrapper sInstance;
    private final User user;

    private UserWrapper(){
        if(sInstance != null){
            throw new RuntimeException("Hey boy, use get instance, no ?");
        }
        user = new User();
    }

    public static UserWrapper getInstance(){
        if(sInstance == null){
            synchronized (UserWrapper.class) {
                if(sInstance == null){
                    sInstance = new UserWrapper();
                }
            }
        }

        return sInstance;
    }

    public String getUserID(){
        return user.getId();
    }

    public LiveData<ArrayList<Playlist>> getPlayListList(){
        LiveData<ArrayList<Playlist>> playlists = user.getPlayListList();

        return playlists;
    }
}
