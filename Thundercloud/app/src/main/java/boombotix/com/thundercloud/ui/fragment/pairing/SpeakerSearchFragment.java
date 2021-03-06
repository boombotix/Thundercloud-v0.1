package boombotix.com.thundercloud.ui.fragment.pairing;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import boombotix.com.thundercloud.BuildConfig;
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
        void onSelectionSkipped();
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

            boolean deviceCheck = device.getName() != null &&
                    (device.getName().contains(BluetoothConstants.BOOMBOT_BASE_NAME) ||
                            device.getName().contains(BluetoothConstants.TEST_HARDWARE_BASE_NAME));

            // we should only scan for official hardware, but for debugging purposes throw in everything
            if (deviceCheck || BuildConfig.DEBUG) {
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

        try {

            for (ParcelUuid uuid : selectedDevice.getUuids()) {
                if (uuid.getUuid().equals(BluetoothConstants.SPP_STANDARD_UUID)) {
                    Timber.d("Found SPP service");
                    PreferenceManager.getDefaultSharedPreferences(ThundercloudApplication.instance())
                            .edit().putString(BluetoothConstants.BOOMBOT_SHAREDPREF_KEY, selectedDevice.getAddress())
                            .apply();

                    onSpeakerSelectedListener.onSpeakerSelected(speakers.get(position));
                    return;
                }
            }

            Snackbar.make(view, "Bluetooth device is not supported", Snackbar.LENGTH_LONG)
                    .setAction("Skip", t -> onSpeakerSelectedListener.onSelectionSkipped()).show();

        } catch (Throwable t) {
            Timber.e(t.getMessage());
            t.printStackTrace();
        }
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
