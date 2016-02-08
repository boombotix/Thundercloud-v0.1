package boombotix.com.thundercloud.ui.activity;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.pairing.ConnectBluetoothFragment;
import boombotix.com.thundercloud.ui.fragment.pairing.SpeakerSearchFragment;

/**
 * Shows fragment asking user to start bluetooth search, then shows  a list of available Boombots.
 * Finishes with selected UUID as a result.
 *
 * @author Theo Kanning
 */
public class SpeakerPairingActivity extends BaseActivity implements
        ConnectBluetoothFragment.OnBluetoothSearchStartedListener,
        SpeakerSearchFragment.OnSpeakerSelectedListener {

    public static final int SUCCESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_pairing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showConnectBluetoothFragment();
    }

    @Override
    public void onBluetoothSearchStarted() {
        showSpeakerSearchFragment();
    }

    @Override
    public void onSpeakerSelected(BluetoothDevice device) {
        //todo connect/store
        setResult(SUCCESS);
        finish();
    }

    private void showConnectBluetoothFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ConnectBluetoothFragment())
                .commit();
    }

    private void showSpeakerSearchFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SpeakerSearchFragment())
                .commit();
    }
}
