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
import butterknife.OnClick;

/**
 * Shows the user an option to initiate bluetooth search, enables bluetooth if necessary, and
 * communicates button press to {@link SpeakerPairingActivity} via {@link
 * OnBluetoothEnabledListener}
 *
 * @author Theo Kanning
 */
public class EnableBluetoothFragment extends Fragment {

    public interface OnBluetoothEnabledListener {

        void onBluetoothEnabled();
    }

    private OnBluetoothEnabledListener onBluetoothEnabledListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect_bluetooth, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle(R.string.connect_bluetooth_title);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onBluetoothEnabledListener = (OnBluetoothEnabledListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnBluetoothEnabledListener");
        }
    }

    @OnClick(R.id.connect)
    public void showSpeakerListFragment() {
        onBluetoothEnabledListener.onBluetoothEnabled();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
