package boombotix.com.thundercloud.ui.fragment;


import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.MainActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class NowPlayingFragment extends BaseFragment implements
        HoloCircleSeekBar.OnCircleSeekBarChangeListener {

    @Bind(R.id.picker)
    HoloCircleSeekBar picker;

    @Bind(R.id.progress_text)
    TextView progressText;

    @Bind(R.id.play_button)
    ImageButton playButton;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public static NowPlayingFragment newInstance() {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        picker.setOnSeekBarChangeListener(this);
        progressText.setOnClickListener(v -> {
            progressText.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
        });

        playButton.setOnClickListener(v -> {
            progressText.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
        });
        ((MainActivity) getActivity()).showSearch();
        return view;
    }

    @Override
    public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

    }

    @Override
    public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {
        progressText.setText(String.valueOf(holoCircleSeekBar.getValue()));
    }
}
