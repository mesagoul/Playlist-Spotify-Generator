package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.fragments.FragmentPagerView;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.PlaylistListFragment;
import android.workshop.dmii.playlistspotifygenerator.interfaces.PagerViewListener;
import android.workshop.dmii.playlistspotifygenerator.models.Playlist;

import java.util.ArrayList;

public class PlaylistDetailFragment extends Fragment implements PagerViewListener {

    private interface DialogListener{
        void onResponse(boolean comfirm);
    }

    public Playlist currentPlaylist;
    private AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_pager_view_detail, container,false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_delete_playlist){

            createDialog(new DialogListener() {
                @Override
                public void onResponse(boolean comfirm) {
                    if(comfirm){
                        removePlaylist();
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    private void removePlaylist(){

        currentPlaylist.remove(new Playlist.PlayListRemoveListener() {
            @Override
            public void onRemoveDone(boolean success, String error) {
                if(success){
                    ((DashboardActivity)getActivity()).loadNewFragment(new PlaylistListFragment(),false, true);
                }else{
                    Toast.makeText(getContext(), error ,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void createDialog(DialogListener listener){

        builder.setMessage(R.string.remove_message)
                .setTitle(R.string.remove_title);
        // 3. Get the AlertDialog from create()

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onResponse(true);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               listener.onResponse(false);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        builder = new AlertDialog.Builder(getActivity());
        loadFragmentViewPager();
    }

    @Override
    public ArrayList<Fragment> initFragmentsForPagerView() {
        ArrayList<Fragment> listFragments = new ArrayList<Fragment>();
        PlaylistDetailArtistsFragment playlistDetailArtistsFragment = new PlaylistDetailArtistsFragment();
        playlistDetailArtistsFragment.currentPlayList = currentPlaylist;
        playlistDetailArtistsFragment.listArtist = currentPlaylist.getArtist();
        PlaylistDetailTracksFragment playlistDetailTracksFragment = new PlaylistDetailTracksFragment();
        playlistDetailTracksFragment.currentPlayList = currentPlaylist;
        playlistDetailTracksFragment.dataListMusic = currentPlaylist.getMusicList();
        listFragments.add(playlistDetailArtistsFragment);
        listFragments.add(playlistDetailTracksFragment);
        return listFragments;
    }

    @Override
    public void loadFragmentViewPager() {
        FragmentPagerView fragment = new FragmentPagerView();
        fragment.setListener(this);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_pager_view, fragment);
        ft.commit();
    }

    @Override
    public String[] initTitlesForPagerView() {
        return new String[]{getResources().getString(R.string.artists),getResources().getString(R.string.tracks)};
    }
}
