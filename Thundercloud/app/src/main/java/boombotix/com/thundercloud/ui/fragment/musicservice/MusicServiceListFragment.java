package boombotix.com.thundercloud.ui.fragment.musicservice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
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

    @Bind(R.id.finish)
    View finish;

    @Bind(R.id.skip)
    View skip;

    @Bind(R.id.music_service_list)
    RecyclerView musicServiceList;


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
            firstTime = getArguments().getBoolean(ARG_FIRST_TIME, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_service_list, container, false);
        ButterKnife.bind(this, view);

        showSkipOptionsIfFirstTime();

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

    private void setTitle(){
        String title = getString(R.string.music_setup_title, speakerName);
        getActivity().setTitle(title);
    }

    private void showSkipOptionsIfFirstTime(){
        if(firstTime){
            skip.setVisibility(View.VISIBLE);
            finish.setVisibility(View.VISIBLE);
        } else {
            skip.setVisibility(View.GONE);
            finish.setVisibility(View.GONE);
        }
    }

    public interface OnMusicServiceSelectedListener {
        //todo replace with music service model object?
        void onMusicServiceSelected(String name);
    }
}
