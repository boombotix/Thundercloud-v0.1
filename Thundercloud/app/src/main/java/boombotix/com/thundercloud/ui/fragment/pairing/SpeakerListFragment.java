package boombotix.com.thundercloud.ui.fragment.pairing;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.SpeakerPairingActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.ButterKnife;

/**
 * Fragment that scans for boombots and presents a list of them to the user, reports selected device
 * to {@link SpeakerPairingActivity} via {@link OnSpeakerSelectedListener}
 *
 * @author Theo Kanning
 */
public class SpeakerListFragment extends BaseFragment {

    public interface OnSpeakerSelectedListener {

        void onSpeakerSelected(String address);
    }

    private ListView speakerList;

    private OnSpeakerSelectedListener onSpeakerSelectedListener;

    private ArrayAdapter<String> speakerAdapter;

    private HashMap<String, BluetoothDevice> speakers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker_list, container, false);
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
        speakerAdapter = new ArrayAdapter<String>()
    }

    /**
     * Creates a subscription to add a speaker to the list whenever one is returned by the ble
     * manager. Clears stored devices.
     */
    private void startSpeakerBleScan() {

    }
}
