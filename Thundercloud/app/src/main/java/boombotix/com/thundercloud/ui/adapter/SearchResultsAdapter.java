package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import boombotix.com.thundercloud.ui.model.SearchResultRowModel;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsViewHolder;

public class SearchResultsAdapter extends RecyclerView.Adapter {
    List<SearchResultRowModel> rowModels;

    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
