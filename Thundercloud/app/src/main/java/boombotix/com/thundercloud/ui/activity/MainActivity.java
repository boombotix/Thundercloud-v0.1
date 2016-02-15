package boombotix.com.thundercloud.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.controller.VoiceSearchController;
import boombotix.com.thundercloud.ui.fragment.MusicListFragment;
import boombotix.com.thundercloud.ui.fragment.MusicPagerFragment;
import boombotix.com.thundercloud.ui.fragment.NowPlayingFragment;
import boombotix.com.thundercloud.ui.fragment.PlayerFragment;
import boombotix.com.thundercloud.ui.fragment.VoiceSearchResultFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/*
* Main activity attaches the main fragment view and the bottom player bar fragment upon search
* through the player fragment, it also attaches the result fragment as an overlay. Communication
* between the player bar and result fragment is controlled through this activity.
*/
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        VoiceSearchController {
    private FragmentManager fm;
    @Bind(R.id.searchText)
    EditText searchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        attachMainFragment();
        attachPlayerFragment();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void attachPlayerFragment() {
        Fragment playerFragment = fm.findFragmentById(R.id.player_fragment);
        if (playerFragment == null) {
            playerFragment = new PlayerFragment();
            fm.beginTransaction()
                    .add(R.id.player_fragment, playerFragment, PlayerFragment.TAG)
                    .commit();
        }
    }

    private void attachMainFragment() {
        fm = getSupportFragmentManager();
        Fragment mainFragment = fm.findFragmentById(R.id.main_fragment);
        if (mainFragment == null) {
            mainFragment = NowPlayingFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.main_fragment, mainFragment, NowPlayingFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        removeFragmentByTag(VoiceSearchResultFragment.TAG);

        if(id == R.id.nav_nowplaying){
            fm.beginTransaction()
                    .replace(R.id.main_fragment,
                            NowPlayingFragment.newInstance(),
                            NowPlayingFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_playlists) {
            changeMusicPagerPage(MusicListFragment.PLAYLIST_SECTION);
        } else if (id == R.id.nav_songs) {
            changeMusicPagerPage(MusicListFragment.SONGS_SECTION);
        } else if (id == R.id.nav_albums) {
            changeMusicPagerPage(MusicListFragment.ALBUMS_SECTION);
        } else if (id == R.id.nav_artists) {
            changeMusicPagerPage(MusicListFragment.ARTISTS_SECTION);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Helper method to open music pager fragment to a certain page
     *
     * @param page
     */
    private void changeMusicPagerPage(int page){

        Fragment musicPagerFragment =  MusicPagerFragment.newInstance(page);
        fm.beginTransaction()
                .replace(R.id.main_fragment, musicPagerFragment)
                .commit();
    }

    /**
     * Adds fragment overlay for te voice search results, if it already exists it  will remove
     * it and recreate it.
     */
    public void addVoiceSearchResultFragment(){
        removeFragmentByTag(VoiceSearchResultFragment.TAG);

        // add overlay
        fm.beginTransaction()
                .add(R.id.overlay_fragment,
                        VoiceSearchResultFragment.newInstance(),
                        VoiceSearchResultFragment.TAG)
                .commit();
        fm.executePendingTransactions();
    }

    private void findAndRefreshFragment(String tag) {
        Fragment voiceSearchResultFragment = fm.findFragmentByTag(tag);
        if (voiceSearchResultFragment != null) {
            fm.beginTransaction().remove(voiceSearchResultFragment).commit();
        }
    }

    /**
     * Updates text on the voice search fragment overlay
     *
     * @param s
     */
    public void updateVoiceSearchResultFragmentText(String s){
       setAndGetVoiceSearchResultFragment().updateText(s);
    }

    /**
     * sets voice search fragment text to the query and allows editing via input box
     *
     * @param s
     */
    public void setVoiceSearchResultFragmentQuery(String s){
        setAndGetVoiceSearchResultFragment().setQuery(s);
    }


    /**
     * Gets viuce search result fragment, if it doesn't exist it will create it first
     *
     * @return the voice search result fragment
     */
    public VoiceSearchResultFragment setAndGetVoiceSearchResultFragment(){
        Fragment fragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        if(fragment == null){
            addVoiceSearchResultFragment();
            fragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        }
        return (VoiceSearchResultFragment) fragment;
    }

    /**
     * Searches fragment by tag and removes it
     *
     * @param tag
     */
    public void removeFragmentByTag(String tag){
        Fragment fragment = fm.findFragmentByTag(tag);
        if(fragment != null) {
            fm.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * Retrieves player fragment and stops any in process searches
     */
    public void stopPlayerSearch(){
       Fragment fragment = fm.findFragmentByTag(PlayerFragment.TAG);
        if(fragment != null){
            ((PlayerFragment) fragment).stopSearch();
        }
    }

    /**
     * Hides search input from toolbar
     */
    public void hideSearch(){
        searchText.setVisibility(View.GONE);
    }


    /**
     * Shows search input in toolbar
     */
    public void showSearch(){
        searchText.setVisibility(View.VISIBLE);
    }

    /**
     * Sets title of toolbar...hmm
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}
