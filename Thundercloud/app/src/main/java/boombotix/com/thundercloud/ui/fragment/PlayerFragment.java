package boombotix.com.thundercloud.ui.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
import boombotix.com.thundercloud.houndify.request.HoundifyRequestAdapter;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.view.CropImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PlayerFragment extends BaseFragment
        implements PhraseSpotterReader.Listener, VoiceSearchListener {

    public static final String TAG = "PlayerFragment";

    @Inject
    HoundifyRequestAdapter houndifyRequestAdapter;

    @Inject
    HoundifyResponseParser houndifyResponseParser;

    private TopLevelActivity activity;

    @Bind(R.id.okhound_button)
    ImageButton okhoundButton;

    String transcript;

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public final String CLIENT_ID = BuildConfig.HOUNDIFY_CLIENT_ID;

    public final String CLIENT_KEY = BuildConfig.HOUNDIFY_CLIENT_KEY;

    private PhraseSpotterReader phraseSpotterReader;

    private VoiceSearch voiceSearch;

    @Bind(R.id.player_blurred_background)
    CropImageView blurredBackround;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;

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
        activity = (TopLevelActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActivity().getActivityComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);
        getSupportActivity().getActivityComponent().inject(this);

        setupBlurredBackground();

        return view;
    }

    private void setupBlurredBackground() {
        View toBlur = ((TopLevelActivity) getSupportActivity())
                .getCaptureableView();
        if (toBlur != null) {

            this.blurredBackround.setImageDrawable(new BitmapDrawable(getResources(),
                    this.screenBlurUiFilter.blurView(toBlur)));
            this.blurredBackround.setColorFilter(
                    ContextCompat.getColor(getActivity(), R.color.playerBarTransparent),
                    PorterDuff.Mode.DARKEN);
            this.blurredBackround.setOffset(0, 1);
        }
    }

    @OnClick(R.id.okhound_button)
    public void okHoundClick(View v) {
        Log.v("PlayerFragment", "Clicky click");
        activity.addVoiceSearchResultFragment();
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

    @Override
    public void onStart() {
        super.onStart();
        startPhraseSpotting();
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
                .setRequestInfo(houndifyRequestAdapter.getHoundRequestInfo(getContext()))
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

        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                startSearch();
            }
        });
    }

    @Override
    public void onError(Exception ex) {

    }
}
