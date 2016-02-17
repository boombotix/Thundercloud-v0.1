package boombotix.com.thundercloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.FrameLayout;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.bluetooth.authentication.MockAuthenticationEndpoint;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.filter.Captureable;
import boombotix.com.thundercloud.ui.fragment.MusicListFragment;
import boombotix.com.thundercloud.ui.fragment.MusicPagerFragment;
import boombotix.com.thundercloud.ui.fragment.NowPlayingFragment;
import boombotix.com.thundercloud.ui.fragment.PlayerFragment;
import boombotix.com.thundercloud.ui.fragment.QueueFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyApi;

public class TopLevelActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;

    @Inject
    SpotifyApi spotifyApi;

    @Inject
    MockAuthenticationEndpoint mockAuthenticationEndpoint;

    @Bind(R.id.searchText)
    EditText searchText;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.main_fragment)
    FrameLayout blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        Fragment mainFragment = fm.findFragmentById(R.id.main_fragment);
        if (mainFragment == null) {

            // TODO actually have  a main fragment
            mainFragment = NowPlayingFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.main_fragment, mainFragment)
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
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setQueueFragment();
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

        if (id == R.id.nav_nowplaying) {
            fm.beginTransaction()
                    .replace(R.id.main_fragment, NowPlayingFragment.newInstance())
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

    private void changeMusicPagerPage(int page) {
        Fragment musicPagerFragment = MusicPagerFragment.newInstance(page);
        fm.beginTransaction()
                .replace(R.id.main_fragment, musicPagerFragment)
                .commit();
    }

    private void setQueueFragment() {
        Fragment queueFragment = QueueFragment.newInstance();
        fm.beginTransaction()
                .replace(R.id.queue_container, queueFragment)
                .commit();
    }

    /**
     * Hides search input from toolbar
     */
    public void hideSearch() {
        searchText.setVisibility(View.GONE);
    }

    public void showSearch() {
        searchText.setVisibility(View.VISIBLE);
    }


    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    /**
     * Callthrough to return the captureable view of the main content fragment, if it implements
     * {@link Captureable}
     *
     * May return null.
     *
     * @return Captureable view of content fragment, OR null.
     */
    public
    @Nullable
    View getCaptureableView() {
        Fragment contentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        if (contentFragment != null && contentFragment instanceof Captureable) {
            return ((Captureable) contentFragment).captureView();
        }
        return null;
    }
}
