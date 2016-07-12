package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.search.spotify.SearchResponse;
import boombotix.com.thundercloud.ui.adapter.SearchResultsAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.model.SearchResultRowModel;
import boombotix.com.thundercloud.ui.view.DividerItemDecorator;
import boombotix.com.thundercloud.ui.view.VerticalSpaceItemDecoration;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import timber.log.Timber;

public class SearchResultsFragment extends BaseFragment {

    public static final String TAG = "SearchResultsFragmentTag";
    public static final int VERTICAL_OFFSET = 64;

    @Bind(R.id.search_results_recyclerview)
    RecyclerView searchResultsRecyclerView;

    @Bind(R.id.voice_search_result_spinner)
    ProgressBar loadingBar;

    SearchResultsAdapter searchResultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results_list, container, false);
        ButterKnife.bind(this, view);
        this.loadingBar.setVisibility(View.VISIBLE);
        return view;
    }

    public void setSearchResults(SearchResponse searchResults){
        Timber.d(searchResults.toString());

        Observable.just(SearchResultRowModel.header(SearchResultsAdapter.HEADER_ARTIST))
        .concatWith(Observable.from(searchResults.getArtists().getArtistList()).map(SearchResultRowModel::artist))
        .concatWith(Observable.just(SearchResultRowModel.header(SearchResultsAdapter.HEADER_ALBUM)))
        .concatWith(Observable.from(searchResults.getAlbums().getAlbumList()).map(SearchResultRowModel::album))
        .concatWith(Observable.just(SearchResultRowModel.header(SearchResultsAdapter.HEADER_TRACK)))
        .concatWith(Observable.from(searchResults.getTracks().getTrackList()).map(SearchResultRowModel::track))
        .concatWith(Observable.just(SearchResultRowModel.header(SearchResultsAdapter.HEADER_PLAYLIST)))
        .concatWith(Observable.from(searchResults.getPlaylists().getPlaylistList()).map(SearchResultRowModel::playlist))
        .collect(ArrayList<SearchResultRowModel>::new, ArrayList<SearchResultRowModel>::add)
        .filter(searchResultRowModels -> searchResultRowModels.size() != 0)
        .subscribe(this::initRecyclerView, t -> Timber.e(t.getMessage())).unsubscribe();
    }

    private void initRecyclerView(List<SearchResultRowModel> searchResultRowModels){
        if(searchResultRowModels.size() == 0) return;
        searchResultsAdapter = new SearchResultsAdapter(searchResultRowModels);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        searchResultsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_OFFSET));
        searchResultsRecyclerView.addItemDecoration(new DividerItemDecorator(getContext(), VERTICAL_OFFSET));
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);

        this.loadingBar.setVisibility(View.GONE);
    }
}
