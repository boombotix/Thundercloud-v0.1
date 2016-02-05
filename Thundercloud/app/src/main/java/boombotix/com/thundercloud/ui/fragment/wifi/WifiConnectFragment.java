package boombotix.com.thundercloud.ui.fragment.wifi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link WifiConnectFragmentCallbacks} interface to handle interaction events. Use the {@link
 * WifiConnectFragment#newInstance} factory method to create an instance of this fragment.
 */
public class WifiConnectFragment extends Fragment {

    private static final String ARG_NETWORK_NAME = "networkName";

    private static final String ARG_SPEAKER_NAME = "speakerName";

    @Bind(R.id.connecting_message)
    TextView message;

    private String networkName;

    private String speakerName;

    private WifiConnectFragmentCallbacks mListener;

    public WifiConnectFragment() {
        // Required empty public constructor
    }

    /**
     * @param networkName name of network that speaker should connect to
     * @param speakerName name of connected speaker, for display purposes
     * @return A new instance of fragment WifiConnectFragment.
     */
    public static WifiConnectFragment newInstance(String networkName, String speakerName) {
        WifiConnectFragment fragment = new WifiConnectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NETWORK_NAME, networkName);
        args.putString(ARG_SPEAKER_NAME, speakerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            networkName = getArguments().getString(ARG_NETWORK_NAME);
            speakerName = getArguments().getString(ARG_SPEAKER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_connect, container, false);
        ButterKnife.bind(this, view);

        setConnectingMessage();
        setTitle();

        return view;
    }

    private void setTitle() {
        String title = getString(R.string.wifi_connect_title, speakerName);
        getActivity().setTitle(title);
    }

    /**
     * Show the network name in the connecting message text
     */
    private void setConnectingMessage() {
        String text = getString(R.string.wifi_connect_connecting, networkName);
        message.setText(text);
    }

    /**
     * Attempts to connect the current speaker to the specific wifi network, sends message to {@link
     * WifiConnectFragmentCallbacks} after completion.
     */
    private void connectSpeakerToNetwork() {
        //todo connect speaker to wifi network
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WifiConnectFragmentCallbacks) {
            mListener = (WifiConnectFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WifiConnectFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface WifiConnectFragmentCallbacks {

        void onWifiConnectSuccess();

        void onWifiConnectFailure();
    }
}
