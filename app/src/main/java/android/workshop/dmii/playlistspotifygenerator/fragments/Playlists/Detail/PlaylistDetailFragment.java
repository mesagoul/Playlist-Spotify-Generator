package android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.activities.DashboardActivity;
import android.workshop.dmii.playlistspotifygenerator.fragments.FragmentPagerView;
import android.workshop.dmii.playlistspotifygenerator.interfaces.PagerViewListener;

import java.util.ArrayList;

public class PlaylistDetailFragment extends Fragment implements PagerViewListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_pager_view_detail, container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadFragmentViewPager();
    }

    @Override
    public ArrayList<Fragment> initFragmentsForPagerView() {
        ArrayList<Fragment> listFragments = new ArrayList<Fragment>();
        PlaylistDetailArtistsFragment playlistDetailArtistsFragment = new PlaylistDetailArtistsFragment();
        PlaylistDetailTracksFragment playlistDetailTracksFragment = new PlaylistDetailTracksFragment();
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
