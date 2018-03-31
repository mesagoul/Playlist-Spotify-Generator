package android.workshop.dmii.playlistspotifygenerator.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.animations.ZoomOutPageTransformer;
import android.workshop.dmii.playlistspotifygenerator.interfaces.PagerViewListener;


import java.util.ArrayList;

/**
 * Created by Lucas on 03/04/2017.
 */

public class FragmentPagerView extends Fragment {
    private ArrayList<Fragment> listFragments;
    private String[] tabTitles;
    // VIEW PAGER
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    private PagerViewListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_pager_view, container,false);
        mPager = (ViewPager) v.findViewById(R.id.pager_view_pager);
        tabLayout = (TabLayout) v.findViewById(R.id.pager_view_tabs);
        listFragments = new ArrayList<Fragment>();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listFragments = listener.initFragmentsForPagerView();
        tabTitles = listener.initTitlesForPagerView();
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), listFragments,tabTitles);
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager);
    }

    public void setListener(PagerViewListener listener) {
        this.listener = listener;
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> listFragments;
        private String tabTitles[];

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragments, String[] tabTitles) {
            super(fm);
            this.listFragments = listFragments;
            this.tabTitles = tabTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // IF there id no title for this tab, no crash and set it with application name
            if(position < tabTitles.length){
                return tabTitles[position];
            }else{
                return getResources().getString(R.string.app_name);
            }
        }
    }
}


