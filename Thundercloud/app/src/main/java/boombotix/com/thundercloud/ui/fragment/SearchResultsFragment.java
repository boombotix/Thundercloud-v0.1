package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.adapter.SearchResultsAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultsFragment extends BaseFragment {

    @Bind(R.id.search_results_recyclerview)
    RecyclerView searchResultsRecyclerView;

    SearchResultsAdapter searchResultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results_list, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        searchResultsAdapter = new SearchResultsAdapter();
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);
    }
}
