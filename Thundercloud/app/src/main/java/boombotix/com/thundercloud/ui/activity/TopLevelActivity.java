package boombotix.com.thundercloud.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.canelmas.let.DeniedPermission;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifySearchEndpoint;
import boombotix.com.thundercloud.base.RxTransformers;
import boombotix.com.thundercloud.model.search.spotify.SearchResponse;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.controller.VoiceSearchController;
import boombotix.com.thundercloud.ui.filter.Captureable;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.fragment.NowPlayingFragment;
import boombotix.com.thundercloud.ui.fragment.PlayerFragment;
import boombotix.com.thundercloud.ui.fragment.QueueFragment;
import boombotix.com.thundercloud.ui.fragment.SearchResultsFragment;
import boombotix.com.thundercloud.ui.fragment.VoiceSearchResultFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;
import timber.log.Timber;

public class TopLevelActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        VoiceSearchController, RuntimePermissionListener {

    private FragmentManager fm;

    @Bind(R.id.searchText)
    EditText searchText;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.player_fragment)
    RelativeLayout playerFragmentHolder;

    @Bind(R.id.main_fragment)
    FrameLayout blur;

    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;

    @Inject
    SpotifySearchEndpoint spotifySearchEndpoint;

    SearchResultsFragment searchResultsFragment;

    private Subject<Boolean, Boolean> screenCreatedObservable;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        screenCreatedObservable = PublishSubject.create();
        setSupportActionBar(toolbar);

        compositeSubscription.add(searchTextObservable()
                .subscribe(this::searchSpotify));

        fm = getSupportFragmentManager();
        attachMainFragment();

        attachPlayerFragment();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setQueueFragment();
    }

    @RxLogObservable
    private Observable<CharSequence> searchTextObservable() {
        return RxTextView.textChanges(searchText)
                .skip(3).debounce(1000, TimeUnit.MILLISECONDS);
    }

    private void searchSpotify(CharSequence searchText) {
        if (searchText.length() > 2) {
            spotifySearchEndpoint
                    .getAllResults(searchText.toString(), "artist,album,playlist,track")
                    .subscribe(res -> {
                    });
        }
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

    public Observable<Boolean> getMainViewCreatedObservable() {
        return this.screenCreatedObservable;
    }

    public void alertMainViewCreated() {
        this.screenCreatedObservable.onNext(true);
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
    protected void onResume() {
        super.onResume();
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

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        compositeSubscription.add(
                RxSearchView.queryTextChanges(searchView)
                        .skip(1)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .filter(s -> s.length() > 0)
                        .subscribe(this::getSearchResults));

        // todo delete this
        searchText.setVisibility(View.GONE);
        return true;
    }

    private void getSearchResults(CharSequence searchText) {
        fm = getSupportFragmentManager();
        searchResultsFragment = new SearchResultsFragment();

        fm.beginTransaction()
                .replace(R.id.main_fragment, searchResultsFragment, SearchResultsFragment.TAG)
                .commit();

        spotifySearchEndpoint.getAllResults(searchText.toString(), "artist,album,playlist,track")
                .compose(RxTransformers.applySchedulers())
                .subscribe(this::showSearchResults, t -> {
                    Timber.e(t.getMessage(), t);
                    t.printStackTrace();
                });
    }

    private void showSearchResults(SearchResponse searchResponse) {
        if(searchResponse == null) return;

        searchResultsFragment.setSearchResults(searchResponse);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_queue) {
            showQueue();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showQueue() {

    }

    private void hideQueue() {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_nowplaying) {
            fm.beginTransaction()
                    .replace(R.id.main_fragment, NowPlayingFragment.newInstance())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Applies blur effect to toolbar background.
     */
    private void updateToolbarBackground(Bitmap bitmap) {

    }

    private void setQueueFragment() {
        Fragment queueFragment = QueueFragment.newInstance();
        fm.beginTransaction()
                .replace(R.id.queue_container, queueFragment)
                .commit();
    }

    /**
     * Adds fragment overlay for te voice search results, if it already exists it  will remove it
     * and recreate it.
     */
    public void addVoiceSearchResultFragment() {
        removeFragmentByTag(VoiceSearchResultFragment.TAG);

        // add overlay
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
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
     */
    public void updateVoiceSearchResultFragmentText(String s) {
        setAndGetVoiceSearchResultFragment().updateText(s);
    }

    /**
     * sets voice search fragment text to the query and allows editing via input box
     */
    public void setVoiceSearchResultFragmentQuery(String s) {
        setAndGetVoiceSearchResultFragment().setQuery(s);
    }

    /**
     * Gets viuce search result fragment, if it doesn't exist it will create it first
     *
     * @return the voice search result fragment
     */
    public VoiceSearchResultFragment setAndGetVoiceSearchResultFragment() {
        Fragment fragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        if (fragment == null) {
            addVoiceSearchResultFragment();
            fragment = fm.findFragmentByTag(VoiceSearchResultFragment.TAG);
        }
        return (VoiceSearchResultFragment) fragment;
    }

    /**
     * Searches fragment by tag and removes it
     */
    public void removeFragmentByTag(String tag) {
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment != null) {
            fm.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * Retrieves player fragment and stops any in process searches
     */
    public void stopPlayerSearch() {
        Fragment fragment = fm.findFragmentByTag(PlayerFragment.TAG);
        if (fragment != null) {
            ((PlayerFragment) fragment).stopSearch();
        }
    }

    /**
     * Hides search input from toolbar
     */
    public void hideSearch() {
        searchText.setVisibility(View.GONE);
//        this.toolbarBackground.getLayoutParams().height = Math.round(50 * getResources()
//                .getDisplayMetrics().density);
    }

    public void showSearch() {
        searchText.setVisibility(View.VISIBLE);
//        this.toolbarBackground.getLayoutParams().height = Math.round(70 * getResources()
//                .getDisplayMetrics().density);
    }

    @OnClick(R.id.searchText)
    protected void onClickSearch() {
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    /**
     * Callthrough to return the captureable view of the main content fragment,
     * if it implements {@link Captureable}
     * <p>
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

    @Override
    public void onShowPermissionRationale(List<String> permissionList, RuntimePermissionRequest permissionRequest) {
        Timber.d("permissions accepted for " + Arrays.toString(permissionList.toArray()));
    }

    @Override
    public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
        Timber.d("Permissions denied for " + Arrays.toString(deniedPermissionList.toArray()));
    }
}
