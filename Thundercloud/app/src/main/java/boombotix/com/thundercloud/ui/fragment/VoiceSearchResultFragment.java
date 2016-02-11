package boombotix.com.thundercloud.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.MainActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;


public class VoiceSearchResultFragment extends BaseFragment {
    public static final String TAG = "VoiceSearchResultFragment";
    private static final String QUERY_ARG = "query";
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        ((MainActivity) getActivity()).stopPlayerSearch();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_search_result, container, false);
        ButterKnife.bind(this, view);
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