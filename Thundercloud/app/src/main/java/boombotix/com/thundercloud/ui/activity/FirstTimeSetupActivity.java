package boombotix.com.thundercloud.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.playback.SpotifyEngine;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Activity that shows all parts of the first-time setup process. Starts with speaker pairing, then
 * connecting speaker to wifi, then connecting music services.
 *
 * @author Theo Kanning
 */
public class FirstTimeSetupActivity extends BaseActivity implements RuntimePermissionListener {

    private static final int REQUEST_CODE_SPEAKER_PAIRING = 0;

    private static final int REQUEST_CODE_SPEAKER_WIFI = 1;

    private static final int REQUEST_CODE_MUSIC_SERVICES = 2;

    private static final int SPOTIFY_AUTH = 666;

    private static final String TAG = "FirstTimeSetupActivity";

    private static final String USER_HAS_COMPLETED_SETUP_KEY = "OnboardingCompleted";

    @Inject
    AuthManager authManager;

    @Inject
    SpotifyEngine spotifyEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getActivityComponent().inject(this);

        boolean onboardingCompleted = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(USER_HAS_COMPLETED_SETUP_KEY, false);

        if (onboardingCompleted) {
            if(authManager.getAccessToken() != null && !this.spotifyEngine.isInitialized()){
                spotifyEngine.spotifyAuthentication(new WeakReference<>(this), SPOTIFY_AUTH);
            }
        } else {
            startSpeakerPairingActivity();
        }
    }

    @DebugLog
    @AskPermission({
            Manifest.permission.BLUETOOTH,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.BLUETOOTH_ADMIN
    })
    private void startSpeakerPairingActivity() {
        Intent intent = new Intent(this, SpeakerPairingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SPEAKER_PAIRING);
    }

    @DebugLog
    private void startSpeakerWifiActivity() {
        Intent intent = new Intent(this, SpeakerWifiActivity.class);
        intent.putExtra(SpeakerWifiActivity.FIRST_TIME_SETUP_KEY, true);
        startActivityForResult(intent, REQUEST_CODE_SPEAKER_WIFI);
    }

    @DebugLog
    private void startMusicServiceSetupActivity() {
        Intent intent = new Intent(this, MusicServiceSetupActivity.class);
        intent.putExtra(MusicServiceSetupActivity.FIRST_TIME_SETUP_KEY, true);
        startActivityForResult(intent, REQUEST_CODE_MUSIC_SERVICES);
    }

    @Override
    public void onShowPermissionRationale(List<String> permissions, final RuntimePermissionRequest request) {
        /**
         * you may show permission rationales in a dialog, wait for user confirmation and retry the permission
         * request by calling request.retry()
         */
    }

    @Override
    public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
        /**
         * Do whatever you need to do about denied permissions:
         *   - update UI
         *   - if permission is denied with 'Never Ask Again', prompt a dialog to tell user
         *   to go to the app settings screen in order to grant again the permission denied
         */
    }

    /**
     * Handles all result codes returned from {@link SpeakerPairingActivity}
     *
     * @param resultCode code returned from activity
     */
    private void handleSpeakerPairingResult(int resultCode) {
        startSpeakerWifiActivity();
    }

    /**
     * Handles all result codes from {@link SpeakerWifiActivity}. If
     */
    private void handleSpeakerWifiResult(int resultCode) {
        switch (resultCode) {
            case SpeakerWifiActivity.SUCCESS:
            case SpeakerWifiActivity.SKIPPED:
                startMusicServiceSetupActivity();
                break;
            case SpeakerWifiActivity.SUCCESS_SKIP_MUSIC:
                finish();
                break;
            default:
                Log.e(TAG, "Unrecognized result code from SpeakerWifiActivity: " + resultCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPOTIFY_AUTH:
                handleSpoifyResult(resultCode, data);
                break;
            case REQUEST_CODE_SPEAKER_PAIRING:
                handleSpeakerPairingResult(resultCode);
                break;
            case REQUEST_CODE_SPEAKER_WIFI:
                handleSpeakerWifiResult(resultCode);
                break;
            case REQUEST_CODE_MUSIC_SERVICES:
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(USER_HAS_COMPLETED_SETUP_KEY, true).commit();

                startTopLevelActivity();
                break;
            default:
                Log.e(TAG, "Unrecognized request code: " + requestCode);
        }
    }

    private void handleSpoifyResult(int resultCode, Intent data) {
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

        startTopLevelActivity();
    }

    private void startTopLevelActivity() {
        Intent intent = new Intent(this, TopLevelActivity.class);
        startActivity(intent);
    }
}
