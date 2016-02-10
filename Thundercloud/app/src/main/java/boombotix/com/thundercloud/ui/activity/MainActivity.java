package boombotix.com.thundercloud.ui.activity;

import android.app.Service;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.MusicListFragment;
import boombotix.com.thundercloud.ui.fragment.MusicPagerFragment;
import boombotix.com.thundercloud.ui.fragment.NowPlayingFragment;
import boombotix.com.thundercloud.ui.fragment.PlayerFragment;
import boombotix.com.thundercloud.ui.fragment.VoiceSearchResultFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fm;
    @Bind(R.id.searchText)
    EditText searchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        Fragment mainFragment = fm.findFragmentById(R.id.main_fragment);
        if (mainFragment == null) {
            // TODO actually have  a main fragment
            mainFragment = NowPlayingFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.main_fragment, mainFragment, NowPlayingFragment.TAG)
                    .commit();
        }

        Fragment playerFragment = fm.findFragmentById(R.id.player_fragment);
        if (playerFragment == null) {
            playerFragment = new PlayerFragment();
            fm.beginTransaction()
                    .add(R.id.player_fragment, playerFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        if(fragment != null){
            fm.beginTransaction().remove(fragment).commit();
        }

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeMusicPagerPage(int page){

        Fragment musicPagerFragment =  MusicPagerFragment.newInstance(page);
        fm.beginTransaction()
                .replace(R.id.main_fragment, musicPagerFragment)
                .commit();
    }

    public void addVoiceSearchFragmentOverlay(String query){
        // find current fragment
        Fragment nowPlayingFragment = fm.findFragmentById(R.id.main_fragment);
        if(nowPlayingFragment.getTag() != NowPlayingFragment.TAG){
            // replace with now playing fragment if it's not the current fragment
            nowPlayingFragment = NowPlayingFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.main_fragment, nowPlayingFragment, NowPlayingFragment.TAG)
                    .commit();
        }
        // hide content in now playing fragment
        ((NowPlayingFragment) nowPlayingFragment).hideContent();

        Fragment voiceSearchResultFragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        if(voiceSearchResultFragment != null){
            fm.beginTransaction().remove(voiceSearchResultFragment).commit();
        }

        // add overlay
        fm.beginTransaction()
                .add(R.id.overlay_fragment,
                        VoiceSearchResultFragment.newInstance(query),
                        VoiceSearchResultFragment.TAG)
                .commit();
    }

    public void removeFragment(Fragment fragment){
        fm.beginTransaction().remove(fragment).commit();
    }

    /**
     * Hides search input from toolbar
     */
    public void hideSearch(){
        searchText.setVisibility(View.GONE);
    }

    public void showSearch(){
        searchText.setVisibility(View.VISIBLE);
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}
