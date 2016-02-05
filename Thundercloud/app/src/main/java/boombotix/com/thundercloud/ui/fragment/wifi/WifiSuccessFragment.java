package boombotix.com.thundercloud.ui.fragment.wifi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link WifiSuccessFragmentCallbacks} interface to handle interaction events.
 * Use the {@link WifiSuccessFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class WifiSuccessFragment extends Fragment {


    private static final String SPEAKER_NAME_KEY = "speakerName";

    private String speakerName;

    private WifiSuccessFragmentCallbacks listener;

    public WifiSuccessFragment() {
        // Required empty public constructor
    }

    /**
     * @param speakerName name of connected speaker
     * @return A new instance of fragment WifiSuccessFragment.
     */
    public static WifiSuccessFragment newInstance(String speakerName) {
        WifiSuccessFragment fragment = new WifiSuccessFragment();
        Bundle args = new Bundle();
        args.putString(SPEAKER_NAME_KEY, speakerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speakerName = getArguments().getString(SPEAKER_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi_success, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WifiSuccessFragmentCallbacks) {
            listener = (WifiSuccessFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WifiSuccessFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    public interface WifiSuccessFragmentCallbacks {
        void onAddMusicSelected();
        void onSkipMusicSelected();
    }
}
