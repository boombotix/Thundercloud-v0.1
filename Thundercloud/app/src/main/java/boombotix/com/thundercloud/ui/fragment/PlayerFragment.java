package boombotix.com.thundercloud.ui.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hound.android.libphs.PhraseSpotterReader;
import com.hound.android.sdk.VoiceSearch;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.android.sdk.VoiceSearchListener;
import com.hound.android.sdk.VoiceSearchState;
import com.hound.android.sdk.audio.SimpleAudioByteStreamSource;
import com.hound.core.model.sdk.HoundResponse;
import com.hound.core.model.sdk.PartialTranscript;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.playback.MusicControls;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.view.CropImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class PlayerFragment extends BaseFragment
        implements PhraseSpotterReader.Listener, VoiceSearchListener {

    public static final String TAG = "PlayerFragment";
    public final String CLIENT_ID = BuildConfig.HOUNDIFY_CLIENT_ID;
    public final String CLIENT_KEY = BuildConfig.HOUNDIFY_CLIENT_KEY;

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private PhraseSpotterReader phraseSpotterReader;
    private VoiceSearch voiceSearch;

    @Inject
    MusicControls musicControls;

    @Inject
    HoundifyRequestTransformer houndifyRequestTransformer;

    @Inject
    HoundifyResponseParser houndifyResponseParser;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;

    @Bind(R.id.player_blurred_background)
    CropImageView blurredBackround;

    @Bind(R.id.okhound_button)
    ImageButton okhoundButton;

    @Bind(R.id.player_play_pause_button)
    AppCompatImageView playButton;

    @Bind(R.id.player_artist_label)
    TextView artistLabel;

    @Bind(R.id.player_track_label)
    TextView trackLabel;

    String transcript;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
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
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);
        getSupportActivity().getActivityComponent().inject(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupDelayedBlurredBackgroundInit(((TopLevelActivity) context)
                .getMainViewCreatedObservable());
    }

    private void setupDelayedBlurredBackgroundInit(Observable<Boolean> viewCreatedObservable) {
        viewCreatedObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> setupBlurredBackground());
    }

    private void setupBlurredBackground() {
        View toBlur = ((TopLevelActivity) getSupportActivity())
                .getCaptureableView();
        if (toBlur != null) {

            this.blurredBackround.setImageDrawable(new BitmapDrawable(getResources(),
                    this.screenBlurUiFilter.blurView(toBlur)).mutate());
            this.blurredBackround.setColorFilter(ContextCompat.getColor(getActivity(), R.color.playerBarTransparent),
                    PorterDuff.Mode.DARKEN);
            this.blurredBackround.setOffset(0.5f, 1);
        }
    }

    @DebugLog
    @OnClick(R.id.okhound_button)
    public void okHoundClick(View v) {
        Log.v("PlayerFragment", "Clicky click");
        ((TopLevelActivity) getSupportActivity()).addVoiceSearchResultFragment();
        if (voiceSearch == null) {
            stopPhraseSpotting();
            startSearch();
        } else {
            // voice search has already started.
            if (voiceSearch.getState() == VoiceSearchState.STATE_STARTED) {
                voiceSearch.stopRecording();
            } else {
                voiceSearch.abort();
            }
        }
    }

    @DebugLog
    @OnClick(R.id.player_play_pause_button)
    public void playButtonOnClick(View v){
        if(musicControls.isPlaying()){
            musicControls.pause();
            v.setBackground(getResources().getDrawable(android.R.drawable.ic_media_play));
        } else {
            musicControls.play();
            v.setBackground(getResources().getDrawable(android.R.drawable.ic_media_pause));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startPhraseSpotting();
        compositeSubscription.add(musicControls.trackChangedObservable().subscribe(this::onTrackChange));
    }

    private void onTrackChange(MusicListItem musicListItem){
        artistLabel.setText(musicListItem.getTitle());
        trackLabel.setText(musicListItem.getSubtitle());
    }

    private void startPhraseSpotting() {
        phraseSpotterReader = new PhraseSpotterReader(new SimpleAudioByteStreamSource());
        phraseSpotterReader.setListener(this);
        phraseSpotterReader.start();
    }

    private void stopPhraseSpotting() {
        phraseSpotterReader.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (voiceSearch != null) {
            voiceSearch.abort();
        }
        // if we don't, we must still be listening for "ok hound" so teardown the phrase spotter
        else if (phraseSpotterReader != null) {
            stopPhraseSpotting();
        }
    }

    public void stopSearch() {
        if (voiceSearch != null) {
            voiceSearch.abort();
        }
    }

    private void startSearch() {
        if (voiceSearch != null) {
            return; // We are already searching
        }

        voiceSearch = new VoiceSearch.Builder()
                .setRequestInfo(houndifyRequestTransformer.getHoundRequestInfo(getContext()))
                .setAudioSource(new SimpleAudioByteStreamSource())
                .setClientId(CLIENT_ID)
                .setClientKey(CLIENT_KEY)
                .setListener(this)
                .build();

        //TODO change button style here

        voiceSearch.start();
    }

    private void updateText(String s) {
        ((TopLevelActivity) getActivity()).updateVoiceSearchResultFragmentText(s);
    }

    private void setQuery(String s) {
        ((TopLevelActivity) getActivity()).setVoiceSearchResultFragmentQuery(s);
    }

    @Override
    public void onTranscriptionUpdate(PartialTranscript partialTranscript) {
        final StringBuilder str = new StringBuilder();
        switch (voiceSearch.getState()) {
            case STATE_STARTED:
                str.append("Listening...");
                break;
            case STATE_SEARCHING:
                str.append("Receiving...");
                break;
            default:
                str.append("Unknown");
                break;
        }
        str.append("\n\n");
        transcript = partialTranscript.getPartialTranscript();
        str.append(transcript);

        updateText(str.toString());
    }

    @Override
    public void onResponse(HoundResponse response, VoiceSearchInfo info) {
        voiceSearch = null;

        setQuery(transcript);
        startPhraseSpotting();
    }

    @Override
    public void onError(Exception e, VoiceSearchInfo info) {
        voiceSearch = null;
        startPhraseSpotting();
    }

    @Override
    public void onAbort(VoiceSearchInfo info) {
        voiceSearch = null;
        startPhraseSpotting();
    }

    @Override
    public void onRecordingStopped() {
        updateText("Recoding stopped");
    }

    @Override
    public void onPhraseSpotted() {
        // It's important to note that when the phrase spotter detects "Ok Hound" it closes
        // the input stream it was provided.

        mainThreadHandler.post(this::startSearch);
    }

    @Override
    public void onError(Exception ex) {
        Timber.e(ex.getMessage());
    }
}
