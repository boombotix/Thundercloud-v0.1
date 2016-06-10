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
import android.widget.TextView;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.hound.android.sdk.TextSearch;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestAdapter;
import boombotix.com.thundercloud.houndify.response.HoundifySubscriber;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class VoiceSearchResultFragment extends BaseFragment {
    public static final String TAG = "VoiceSearchResultFragment";
    private static final String QUERY_ARG = "query";

    @Inject
    HoundifyRequestAdapter houndifyRequestAdapter;

    @Inject
    HoundifyResponseParser houndifyResponseParser;

    @Inject
    HoundifySubscriber houndifySubscriber;

    @Bind(R.id.voice_search_result_tap_to_edit)
    TextView tapToEdit;

    @Bind(R.id.voice_search_result_text)
    TextView queryText;

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
        ((TopLevelActivity) getActivity()).stopPlayerSearch();
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
                            .setRequestInfo(houndifyRequestAdapter.getHoundRequestInfo(getContext()))
                            .setClientId(BuildConfig.HOUNDIFY_CLIENT_ID)
                            .setClientKey(BuildConfig.HOUNDIFY_CLIENT_KEY)
                            .setDebug(BuildConfig.DEBUG)
                            .setQuery(q)
                            .build();


        Observable.defer(() -> createResultObservable(textSearch))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.houndifySubscriber);
    }

    @Nullable
    @RxLogObservable
    private Observable<TextSearch.Result> createResultObservable(TextSearch textSearch){
        try {
            Timber.d("Attempting to create voice search with houndify");
            return Observable.just(textSearch.search());
        } catch (TextSearch.TextSearchException e) {
            Timber.e("Failed to start voice search");
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