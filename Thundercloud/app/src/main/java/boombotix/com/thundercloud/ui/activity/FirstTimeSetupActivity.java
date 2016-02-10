package boombotix.com.thundercloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;

/**
 * Activity that shows all parts of the first-time setup process. Starts with speaker pairing, then
 * connecting speaker to wifi, then connecting music services.
 *
 * @author Theo Kanning
 */
public class FirstTimeSetupActivity extends BaseActivity {

    private static final int REQUEST_CODE_SPEAKER_PAIRING = 0;

    private static final int REQUEST_CODE_SPEAKER_WIFI = 1;

    private static final int REQUEST_CODE_MUSIC_SERVICES = 2;

    private static final String TAG = "FirstTimeSetupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startSpeakerPairingActivity();
    }

    private void startSpeakerPairingActivity() {
        Intent intent = new Intent(this, SpeakerPairingActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SPEAKER_PAIRING);
    }

    private void startSpeakerWifiActivity() {
        Intent intent = new Intent(this, SpeakerWifiActivity.class);
        intent.putExtra(SpeakerWifiActivity.FIRST_TIME_SETUP_KEY, true);
        startActivityForResult(intent, REQUEST_CODE_SPEAKER_WIFI);
    }

    private void startMusicServiceSetupActivity(){
        Intent intent = new Intent(this, MusicServiceSetupActivity.class);
        intent.putExtra(MusicServiceSetupActivity.FIRST_TIME_SETUP_KEY, true);
        startActivityForResult(intent, REQUEST_CODE_MUSIC_SERVICES);
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
            case REQUEST_CODE_SPEAKER_PAIRING:
                handleSpeakerPairingResult(resultCode);
                break;
            case REQUEST_CODE_SPEAKER_WIFI:
                handleSpeakerWifiResult(resultCode);
                break;
            case REQUEST_CODE_MUSIC_SERVICES:
                finish();
                break;
            default:
                Log.e(TAG, "Unrecognized request code: " + requestCode);
        }
    }
}
