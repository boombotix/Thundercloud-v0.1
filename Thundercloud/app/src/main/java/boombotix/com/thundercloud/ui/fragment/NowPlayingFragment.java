package boombotix.com.thundercloud.ui.fragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.Captureable;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.view.CropImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class NowPlayingFragment extends BaseFragment implements
        HoloCircleSeekBar.OnCircleSeekBarChangeListener,
        Captureable {

    public static final String TAG = "NowPlayingFragment";
    @Bind(R.id.picker)
    HoloCircleSeekBar picker;

    @Bind(R.id.progress_text)
    TextView progressText;

    @Bind(R.id.play_button)
    ImageButton playButton;

    @Bind(R.id.now_playing_album_art)
    CropImageView albumArt;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;


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
        super.showSearch();
        super.hideTabs();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActivity().getActivityComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO: this whole bit will need to be redone when we're getting actual album art
        albumArt.setImageBitmap(this.screenBlurUiFilter.cropToScreenSize(BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_album_art_test)));
        super.alertActivityMainViewCreated();
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

    @Override
    public View captureView() {
        View view = getActivity().findViewById(R.id.now_playing_layout);

        return view;
    }
}
