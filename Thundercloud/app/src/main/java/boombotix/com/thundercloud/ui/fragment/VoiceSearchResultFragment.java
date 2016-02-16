package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hound.android.sdk.TextSearch;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.houndify.HoundifyHelper;
import boombotix.com.thundercloud.houndify.model.HoundifyResponse;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class VoiceSearchResultFragment extends BaseFragment {
    public static final String TAG = "VoiceSearchResultFragment";
    private static final String QUERY_ARG = "query";
    @Inject
    HoundifyHelper houndifyHelper;
    @Bind(R.id.voice_search_result_tap_to_edit)
    TextView tapToEdit;
    @Bind(R.id.voice_search_result_text)
    TextView queryText;
    @Bind(R.id.voice_search_result_edit)
    EditText editText;

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


    private void doSearch(String q) {
       TextSearch textSearch = new TextSearch.Builder()
                            .setRequestInfo(houndifyHelper.getHoundRequestInfo(getContext()))
                            .setClientId(BuildConfig.HOUNDIFY_CLIENT_ID)
                            .setClientKey(BuildConfig.HOUNDIFY_CLIENT_KEY)
                            .setQuery(q)
                            .build();


        Observable.defer(() -> {
            try {
                return Observable.just(textSearch.search());
            } catch (TextSearch.TextSearchException e) {
                e.printStackTrace();
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    HoundifyResponse houndifyResponse =  houndifyHelper.parseResponse(result.getResponse());
                    Toast.makeText(getContext(),houndifyResponse.getTracks().get(0).getTrackName(), Toast.LENGTH_LONG).show();
                });
    }

    public void updateText(String s){
        queryText.setText(s);
    }

    public void setQuery(String s){
        timerHandler.post(timerRunnable);
        queryText.setText(s);
        tapToEdit.setVisibility(View.VISIBLE);
        editText.setText(s);
    }

}