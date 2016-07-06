package boombotix.com.thundercloud.ui.fragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.api.SpotifyTrackEndpoint;
import boombotix.com.thundercloud.base.RxTransformers;
import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.search.spotify.Track;
import boombotix.com.thundercloud.playback.MusicControls;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.Captureable;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.view.CropImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import timber.log.Timber;

public class NowPlayingFragment extends BaseFragment implements
        HoloCircleSeekBar.OnCircleSeekBarChangeListener,
        Captureable {

    public static final String TAG = "NowPlayingFragment";

    @Bind(R.id.picker)
    HoloCircleSeekBar picker;

    @Bind(R.id.progress_text)
    TextView progressText;

    @Bind(R.id.player_play_pause_button)
    ImageButton playButton;

    @Bind(R.id.now_playing_album_art)
    CropImageView albumArt;

    @Inject
    SpotifyTrackEndpoint spotifyTrackEndpoint;

    @Inject
    SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;

    @Inject
    MusicControls musicControls;

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

    @DebugLog
    @Override
    public void onStart() {
        super.onStart();
        compositeSubscription.add(musicControls.trackChangedObservable().subscribe(this::onTrackChanged, this::logErrors));
    }

    @DebugLog
    private void onTrackChanged(MusicListItem musicListItem){
        compositeSubscription.add(spotifyTrackEndpoint.getTrack(cleanSpotifyUri(musicListItem.getUri()))
                .compose(RxTransformers.applySchedulers())
                .subscribe(this::onArtworkResult, this::logErrors));
    }

    private void logErrors(Throwable throwable){
        Timber.e(throwable.getMessage());
    }

    @DebugLog
    private String cleanSpotifyUri(String uri){
        return uri.replace("spotify:track:", "");
    }

    @DebugLog
    private void onArtworkResult(Track track){
        Glide.with(this).load(track.getAlbum().getImages().get(0).getUrl()).into(albumArt);
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
        return getActivity().findViewById(R.id.now_playing_layout);
    }
}
