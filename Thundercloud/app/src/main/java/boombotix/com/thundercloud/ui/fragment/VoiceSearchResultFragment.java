package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.hound.android.sdk.TextSearch;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.houndify.HoundifyHelper;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
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

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    TextSearch textSearch = new TextSearch.Builder()
                            .setClientId(PlayerFragment.CLIENT_ID)
                            .setClientKey(PlayerFragment.CLIENT_KEY)
                            .setRequestInfo(houndifyHelper.getHoundRequestInfo(getContext()))
                            .setQuery(String.valueOf(v.getText()))
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
                                Log.e("yep", result.getResponse().getResults().get(0).getNativeData().get("Tracks").get(0).toString());
                            });
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    public void updateText(String s){
        queryText.setText(s);
    }

    public void setQuery(String s){
        queryText.setText(s);
        tapToEdit.setVisibility(View.VISIBLE);
        editText.setText(s);
        queryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapToEdit.setVisibility(View.GONE);
                queryText.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
            }
        });
    }

}