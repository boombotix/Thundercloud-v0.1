package boombotix.com.thundercloud.ui.fragment.musicservice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import butterknife.ButterKnife;

/**
 * Shows a list of music services and gives the user the option of connecting to one or several of
 * them. Can be skipped if part of first-time setup.
 *
 * @author Theo Kanning
 */
public class MusicServiceListFragment extends Fragment {

    private static final String ARG_SPEAKER_NAME = "speaker_name";
    private static final String ARG_FIRST_TIME = "first_time";

    private String speakerName;
    private boolean firstTime;

    private OnMusicServiceSelectedListener listener;

    public MusicServiceListFragment() {
        // Required empty public constructor
    }

    /**
     * @param speakerName name of connected speaker
     * @return A new instance of fragment MusicServiceListFragment.
     */
    public static MusicServiceListFragment newInstance(String speakerName, boolean firstTime) {
        MusicServiceListFragment fragment = new MusicServiceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPEAKER_NAME, speakerName);
        args.putBoolean(ARG_FIRST_TIME, firstTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speakerName = getArguments().getString(ARG_SPEAKER_NAME);
            firstTime = getArguments().getBoolean(ARG_FIRST_TIME,false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_service_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMusicServiceSelectedListener) {
            listener = (OnMusicServiceSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMusicServiceSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnMusicServiceSelectedListener {
        //todo replace with music service model object?
        void onMusicServiceSelected(String name);
    }
}
