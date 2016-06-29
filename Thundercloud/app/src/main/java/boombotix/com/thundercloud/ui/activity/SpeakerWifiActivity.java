package boombotix.com.thundercloud.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.model.constants.BluetoothConstants;
import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.wifi.WifiConnectFragment;
import boombotix.com.thundercloud.ui.fragment.wifi.WifiConnectFragment.WifiConnectFragmentCallbacks;
import boombotix.com.thundercloud.ui.fragment.wifi.WifiListFragment;
import boombotix.com.thundercloud.ui.fragment.wifi.WifiListFragment.WifiListFragmentCallbacks;
import boombotix.com.thundercloud.ui.fragment.wifi.WifiSuccessFragment;

/**
 * Handles fragments for showing a list of available wifi networks and connecting the speaker to one
 * of them. Shows different messages and an additional success fragment if doing a first-time setup,
 * as specified in the FIRST_TIME_SETUP_KEY extra.
 *
 * Result codes: SUCCESS - wifi paired successfully, no further action needed SKIPPED - User has
 * chosen to skip this step, only available during first time setup SUCCESS_ADD_MUSIC - Succeeded
 * and user has chosen to proceed to adding music services, only available during first time setup
 *
 * @author Theo Kanning
 */
public class SpeakerWifiActivity extends BaseActivity implements WifiListFragmentCallbacks,
        WifiConnectFragmentCallbacks, WifiSuccessFragment.WifiSuccessFragmentCallbacks {

    public static final String FIRST_TIME_SETUP_KEY = "SpeakerWifiActivity.FirstTimeSetupKey";

    public static final int SUCCESS = 0;

    public static final int SKIPPED = 1;

    public static final int SUCCESS_SKIP_MUSIC = 2;

    private boolean firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_wifi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFirstTimeExtra();
        showWifiListFragment();
    }

    /**
     * Reads first time extra and stores the result. Default is false.
     */
    private void getFirstTimeExtra() {
        firstTime = getIntent().getBooleanExtra(FIRST_TIME_SETUP_KEY, false);
    }

    private String getSpeakerName() {
        String macAddress = PreferenceManager
                .getDefaultSharedPreferences(ThundercloudApplication.instance())
                .getString(BluetoothConstants.BOOMBOT_SHAREDPREF_KEY, null);

        if(macAddress == null) return "Boombot";

        return BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress).getName();
    }

    /**
     * Shows a {@link WifiListFragment} and sends the current speaker name and whether or not this
     * is first-time setup
     */
    private void showWifiListFragment() {
        WifiListFragment fragment = WifiListFragment.newInstance(getSpeakerName(), true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    /**
     * Shows a {@link WifiConnectFragment} and the info of the network that the speaker should use.
     *
     * @param networkName the wifi network that the speaker should use
     */
    private void showWifiConnectFragment(String networkName, String password) {
        WifiConnectFragment fragment = WifiConnectFragment.newInstance(networkName, getSpeakerName(), password);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    /**
     * Shows a {@link WifiSuccessFragment} that gives the user the option to continue to adding
     * music services. Only used during first time setup.
     */
    private void showWifiSuccessFragment() {
        WifiSuccessFragment fragment = WifiSuccessFragment.newInstance(getSpeakerName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onWifiNetworkChosen(WifiNetwork network) {
        //todo send credentials to fragment
        showWifiConnectFragment(network.getSsid(), network.getCredentials().getPassword());
    }

    @Override
    public void onWifiSetupSkipped() {
        setResult(SKIPPED);
        finish();
    }

    /**
     * If this is not first time set up, we're done. If it is, then show {@link
     * WifiSuccessFragment}
     */
    @Override
    public void onWifiConnectSuccess() {

        if (firstTime) {
            showWifiSuccessFragment();
        } else {
            setResult(SUCCESS);
            finish();
        }
    }

    @Override
    public void onWifiConnectFailure() {
        //let user try again
        showWifiListFragment();
    }

    @Override
    public void onAddMusicSelected() {
        //finish and tell calling activity to proceed to adding music
        setResult(SUCCESS);
        finish();
    }

    @Override
    public void onSkipMusicSelected() {
        setResult(SUCCESS_SKIP_MUSIC);
        finish();
    }
}
