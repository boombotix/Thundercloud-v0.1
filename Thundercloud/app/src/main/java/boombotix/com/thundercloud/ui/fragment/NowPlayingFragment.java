package boombotix.com.thundercloud.ui.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.MainActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class NowPlayingFragment extends BaseFragment implements
        HoloCircleSeekBar.OnCircleSeekBarChangeListener {
    public static final String TAG = "NowPlayingFragment";
    @Bind(R.id.picker)
    HoloCircleSeekBar picker;
    @Bind(R.id.progress_text)
    TextView progressText;
    @Bind(R.id.play_button)
    ImageButton playButton;
    @Bind(R.id.nowplaying_content)
    RelativeLayout mainContent;
    @Bind(R.id.now_playing_album_art)
    ImageView nowPlayingAlbumArt;

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

        Picasso.with(getContext())
                .load(R.drawable.daftpunk_bg)
                .fit()
                .into(nowPlayingAlbumArt);
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

    public void hideContent(){
        mainContent.setVisibility(View.GONE);
    }

    public void showContent(){
        mainContent.setVisibility(View.VISIBLE);
    }
}
