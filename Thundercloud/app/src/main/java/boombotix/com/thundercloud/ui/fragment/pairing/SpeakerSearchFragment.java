package boombotix.com.thundercloud.ui.fragment.pairing;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.SpeakerPairingActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

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
    ListView speakerList;

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

        return view;
    }

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

    /**
     * Initializes the speaker list with a new adapter
     */
    private void initSpeakerList() {
        speakerAdapter = new ArrayAdapter<>(getContext(), R.layout.row_speaker, new ArrayList<>());
        speakerList.setAdapter(speakerAdapter);
        speakerList.setOnItemClickListener(speakerClickListener);
    }

    /**
     * Creates a subscription to add a speaker to the list whenever one is returned by the ble
     * manager. Clears stored devices.
     */
    private void startSpeakerBleScan() {
        speakers = new ArrayList<>();
        showScanView();
        //todo start ble scan
        //todo subscribe to ble results
    }

    /**
     * Shows progress indicator before a device is found
     */
    private void showScanView() {
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
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
        onSpeakerSelectedListener.onSpeakerSelected(selectedDevice);
    };
}
