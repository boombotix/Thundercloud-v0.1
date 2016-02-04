package boombotix.com.thundercloud.ui.fragment.pairing;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.SpeakerPairingActivity;
import butterknife.ButterKnife;

/**
 * Enables bluetooth if necessary, shows the user an option to initiate bluetooth search, and
 * communicates button press to {@link SpeakerPairingActivity} via {@link OnPairingStartedListener}
 *
 * @author Theo Kanning
 */
public class ConnectBluetoothFragment extends Fragment {

    public interface OnPairingStartedListener {
        void onPairingStarted();
    }

    private OnPairingStartedListener onPairingStartedListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect_bluetooth, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle(R.string.connect_bluetooth_fragment_title);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onPairingStartedListener = (OnPairingStartedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPairingStarted");
        }
    }
}
