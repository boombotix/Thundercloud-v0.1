package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.hound.android.sdk.TextSearch;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.base.RxTransformers;
import boombotix.com.thundercloud.houndify.model.Album;
import boombotix.com.thundercloud.houndify.model.Artist;
import boombotix.com.thundercloud.houndify.model.MusicSearchResultsNativeData;
import boombotix.com.thundercloud.houndify.model.Track;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.playback.MusicControls;
import boombotix.com.thundercloud.playback.PlaybackQueue;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hugo.weaving.DebugLog;
import rx.Observable;
import timber.log.Timber;

public class VoiceSearchResultFragment extends BaseFragment {
    public static final String TAG = "VoiceSearchResultFragment";
    private static final String QUERY_ARG = "query";

    @Inject
    MusicControls musicControls;

    @Inject
    PlaybackQueue playbackQueue;

    @Inject
    HoundifyRequestTransformer houndifyRequestTransformer;

    @Inject
    HoundifyResponseParser houndifyResponseParser;

    @Bind(R.id.voice_search_result_tap_to_edit)
    TextView tapToEdit;

    @Bind(R.id.voice_search_result_text)
    TextView queryText;

    @Bind(R.id.voice_search_result_spinner)
    ProgressBar voiceSearchResultsSpinner;

    @Bind(R.id.voice_search_result_edit)
    EditText editText;
    // delay in ms before query happens automagically
    private final long queryDelay = 5000;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            doSearch(String.valueOf(queryText.getText()));
        }
    };

    public VoiceSearchResultFragment() {
    }

    public static VoiceSearchResultFragment newInstance() {
        VoiceSearchResultFragment fragment = new VoiceSearchResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActivity().getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_search_result, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @DebugLog
    @OnClick(R.id.voice_search_result_text)
    public void onVoiceSearchResultClick(View v) {
        timerHandler.removeCallbacks(timerRunnable);
        tapToEdit.setVisibility(View.GONE);
        queryText.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
    }

    @OnEditorAction(R.id.voice_search_result_edit)
    public boolean onQueryEdit(TextView v, int actionId, KeyEvent event){
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doSearch(String.valueOf(v.getText()));
            return true;
        }
        return false;
    }

    @DebugLog
    void doSearch(String q) {
        TextSearch textSearch = new TextSearch.Builder()
                .setRequestInfo(houndifyRequestTransformer.getHoundRequestInfo(getContext()))
                .setClientId(BuildConfig.HOUNDIFY_CLIENT_ID)
                .setClientKey(BuildConfig.HOUNDIFY_CLIENT_KEY)
                .setDebug(BuildConfig.DEBUG)
                .setQuery(q)
                .build();

        Observable.defer(() -> createResultObservable(textSearch))
                .compose(RxTransformers.applySchedulers())
                .map(result -> houndifyResponseParser.parseMusicSearchResponse(result.getResponse()))
                .map(this::convertToListItems)
                .flatMap(listItemObservable -> listItemObservable)
                .subscribe(
                        listItem -> {
                            playbackQueue.addToQueue(listItem);
                            musicControls.play();
                            doneLoadingResults();
                        },
                        t -> Timber.e(t.getMessage()));
    }

    private void loadingResults(){
        tapToEdit.setVisibility(View.GONE);
        queryText.setVisibility(View.GONE);
        voiceSearchResultsSpinner.setVisibility(View.VISIBLE);
    }

    private void doneLoadingResults(){
        voiceSearchResultsSpinner.setVisibility(View.GONE);
    }

    @RxLogObservable
    private Observable<MusicListItem> convertToListItems(MusicSearchResultsNativeData data){
        return  Observable.from(data.getTracks()).map(Track::convertToListItem)
                .concatWith(Observable.from(data.getArtists()).map(Artist::convertToListItem))
                .concatWith(Observable.from(data.getAlbums()).map(Album::convertToListItem));
    }

    @Nullable
    @RxLogObservable
    private Observable<TextSearch.Result> createResultObservable(TextSearch textSearch){
        try {
            Timber.d("Attempting to create voice search with houndify");
            return Observable.just(textSearch.search());
        } catch (TextSearch.TextSearchException e) {
            Timber.e("Failed to start voice search");
            Timber.e(e.getMessage());
            return null;
        }
    }

    public void updateText(String s){
        queryText.setText(s);
    }

    public void setQuery(String s){
        timerHandler.postDelayed(timerRunnable, queryDelay);
        queryText.setText(s);
        tapToEdit.setVisibility(View.VISIBLE);
        editText.setText(s);
    }
}