package boombotix.com.thundercloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.music.Service;
import boombotix.com.thundercloud.playback.SpotifyEngine;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.musicservice.MusicServiceListFragment;
import hugo.weaving.DebugLog;
import timber.log.Timber;

public class MusicServiceSetupActivity extends BaseActivity
        implements MusicServiceListFragment.OnMusicServiceSelectedListener {

    public static final String FIRST_TIME_SETUP_KEY = "firstTimeSetup";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 666;

    @Inject
    AuthManager authManager;

    @Inject
    SpotifyEngine spotifyEngine;

    private boolean firstTime;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_service_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getActivityComponent().inject(this);

        getFirstTimeExtra();
        showMusicServiceListFragment();
    }

    /**
     * Reads first time extra and stores the result. Default is false.
     */
    private void getFirstTimeExtra() {
        firstTime = getIntent().getBooleanExtra(FIRST_TIME_SETUP_KEY, false);
    }

    private void showMusicServiceListFragment() {
        //todo get speaker name
        MusicServiceListFragment fragment = MusicServiceListFragment.newInstance("Boombot", firstTime);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onMusicServiceSelected(Service service) {
        switch (service){
            case Spotify:
                spotifyAuthentication();
                break;
            case Slacker:
                break;
            default:
                throw new RuntimeException("Service not supported");
        }
    }

    @Override
    public void onMusicServiceFinished() {
        finish();
    }

    @DebugLog
    private void spotifyAuthentication(){
        spotifyEngine.spotifyAuthentication(new WeakReference<>(this), REQUEST_CODE);
    }

    @DebugLog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            switch (response.getType()) {
                case TOKEN:
                    Timber.d(response.getAccessToken());
                    authManager.setAccessToken(response.getAccessToken());
                    spotifyEngine.initializePlayerWithToken(response.getAccessToken());
                    break;
                case ERROR:
                    Timber.e(response.getError());
                    break;
                default:
                    Timber.d("Login canceled");
            }
        }
    }
}
