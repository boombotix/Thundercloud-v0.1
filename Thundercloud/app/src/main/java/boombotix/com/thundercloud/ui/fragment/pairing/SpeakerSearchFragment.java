package boombotix.com.thundercloud.ui.fragment.pairing;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.model.constants.BluetoothConstants;
import boombotix.com.thundercloud.ui.activity.SpeakerPairingActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Fragment that scans for boombots and presents a list of them to the user, reports selected device
 * to {@link SpeakerPairingActivity} via {@link OnSpeakerSelectedListener}. Shows message if no
 * speakers are found
 *
 * @author Theo Kanning
 */
public class SpeakerSearchFragment extends BaseFragment {

    public interface OnSpeakerSelectedListener {

        void onSpeakerSelected(BluetoothDevice device);
    }

    @Bind(R.id.speaker_list)
    ListView speakerListView;

    @Bind(R.id.search_container)
    View searchContainer;

    @Bind(R.id.no_results_container)
    View noResultsContainer;

    private OnSpeakerSelectedListener onSpeakerSelectedListener;

    private ArrayAdapter<String> speakerAdapter;

    private ArrayList<BluetoothDevice> speakers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker_search, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle(R.string.speaker_search_title);
        initSpeakerList();
        startSpeakerBleScan();
        return view;
    }

    /**
     * Initializes the speaker list with a new adapter
     */
    private void initSpeakerList() {
        speakerAdapter = new ArrayAdapter<>(getContext(), R.layout.row_speaker, R.id.speaker_name, new ArrayList<>());
        speakers = new ArrayList<>();
        speakerListView.setAdapter(speakerAdapter);
        speakerListView.setOnItemClickListener(speakerClickListener);
    }

    /**
     * Creates a subscription to add a speaker to the list whenever one is returned by the ble
     * manager. Clears stored devices.
     */
    @DebugLog
    private void startSpeakerBleScan() {
        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

        for (BluetoothDevice device : devices) {
            Log.d(this.getClass().getName(), String.format("Name: %s, Address: %s, Type: %s, Class: %s",
                    device.getName(), device.getAddress(), device.getType(),
                    device.getBluetoothClass() != null ? device.getBluetoothClass().getDeviceClass() : ""));

            if (device.getName() != null &&
                    (device.getName().contains(BluetoothConstants.BOOMBOT_BASE_NAME) ||
                            device.getName().contains(BluetoothConstants.TEST_HARDWARE_BASE_NAME))) {

                speakerAdapter.add(device.getName());
                speakers.add(device);
            }
        }
    }

    /**
     * Shows progress indicator before a device is found
     */
    private void showScanView() {
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
        //todo determine if we are showing a progress indicator or changing text before a speaker has been found
    }

    /**
     * Shows list of speakers after one has been found
     */
    private void showSearchResultsView() {
        showScanView();
    }

    /**
     * Shows a message when no speakers are found
     */
    private void showNoResultsView() {
        searchContainer.setVisibility(View.GONE);
        searchContainer.setVisibility(View.VISIBLE);
    }

    /**
     * When a speaker is pressed in the list, send the corresponding BluetoothDevice to the {@link
     * OnSpeakerSelectedListener}
     */
    private AdapterView.OnItemClickListener speakerClickListener = (parent, view, position, id) -> {
        //todo stop scan
        BluetoothDevice selectedDevice = speakers.get(position);
        onSpeakerSelectedListener.onSpeakerSelected(speakers.get(position));

        try {

            for (ParcelUuid uuid : selectedDevice.getUuids()) {
                if (uuid.getUuid().equals(BluetoothConstants.SPP_STANDARD_UUID)) {
                    Timber.d("Found SPP service");
                    PreferenceManager.getDefaultSharedPreferences(ThundercloudApplication.instance())
                            .edit().putString(BluetoothConstants.BOOMBOT_SHAREDPREF_KEY, selectedDevice.getAddress());
                }
            }

            // system can start discovery at any time, it's good practice to cancel before using bluetooth
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            BluetoothSocket socket = selectedDevice.createRfcommSocketToServiceRecord(BluetoothConstants.SPP_STANDARD_UUID);
            socket.connect();

            Timber.d("Is connected: " + socket.isConnected() + " on UUID " + BluetoothConstants.SPP_STANDARD_UUID);

            byte[] buffer = ("WIFI: WPA:Hyperfighting" + Character.toString((char) 7) + "happytrail219\0").getBytes(Charset.forName("ASCII").displayName());
            OutputStream stream = socket.getOutputStream();

            stream.write(buffer);
            stream.flush();

            // todo get result back from speaker before closing
            stream.close();
        } catch (Throwable t) {
            Timber.e(t.getMessage());
            t.printStackTrace();
        }

        Timber.d(String.format("Clicked %s", speakers.get(position)));
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSpeakerSelectedListener = (OnSpeakerSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSpeakerSelectedListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
