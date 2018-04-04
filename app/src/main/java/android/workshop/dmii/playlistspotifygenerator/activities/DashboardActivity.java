package android.workshop.dmii.playlistspotifygenerator.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.fragments.PlayerFragment;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.CreatePlaylistFragment;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.GeneratePlaylistFragment;
import android.workshop.dmii.playlistspotifygenerator.fragments.Playlists.PlaylistListFragment;
import android.workshop.dmii.playlistspotifygenerator.models.User;

/**
 * Created by admin on 06/03/2018.
 */

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        // SET TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // ADD NAVIGATION BAR TO CONTEXT
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView tv = (TextView)hView.findViewById(R.id.user_name);
        tv.setText(User.getInstance().getName());

        loadNewFragment(new PlaylistListFragment(), false, true);
        loadPlayer();
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.create_playlist){
            loadNewFragment(new CreatePlaylistFragment(), false, true);
        }else if(id == R.id.generate_playlist){
            loadNewFragment(new GeneratePlaylistFragment(), false, true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // load new fragment to show in activity
    public void loadNewFragment(Fragment newFragment , boolean isCloseFragment, boolean isFirstFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, newFragment);
        if(!isFirstFragment){
            if(isCloseFragment){
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            }else{
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    public void loadPlayer(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_player, new PlayerFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


}
