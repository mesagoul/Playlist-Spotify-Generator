package android.workshop.dmii.playlistspotifygenerator.interfaces;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/04/2017.
 */

public interface PagerViewListener {
    ArrayList<Fragment> initFragmentsForPagerView();
    void loadFragmentViewPager();
    String[] initTitlesForPagerView();
}
