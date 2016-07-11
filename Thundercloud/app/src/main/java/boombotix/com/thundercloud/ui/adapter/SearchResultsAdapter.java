package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.model.SearchResultRowModel;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsAlbumViewHolder;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsArtistViewHolder;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsHeaderViewHolder;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsPlaylistViewHolder;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsTrackViewHolder;

public class SearchResultsAdapter extends RecyclerView.Adapter {
    List<SearchResultRowModel> rowModels;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @SearchResultRowModel.SearchResultView int viewType) {
        switch (viewType){
            case SearchResultRowModel.SEARCH_RESULTS_ARTIST_HEADER:
                View artistHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_artist_header, parent);
                return new SearchResultsHeaderViewHolder(artistHeaderView);
            case SearchResultRowModel.SEARCH_RESULTS_ARTIST_ITEM:
                View artistItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_artist_item, parent);
                return new SearchResultsArtistViewHolder(artistItemView);
            case SearchResultRowModel.SEARCH_RESULTS_ALBUM_HEADER:
                View albumHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_album_header, parent);
                return new SearchResultsHeaderViewHolder(albumHeaderView);
            case SearchResultRowModel.SEARCH_RESULTS_ALBUM_ITEM:
                View albumItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_album_item, parent);
                return new SearchResultsAlbumViewHolder(albumItemView);
            case SearchResultRowModel.SEARCH_RESULTS_TRACK_HEADER:
                View trackHeadeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_track_header, parent);
                return new SearchResultsHeaderViewHolder(trackHeadeView);
            case SearchResultRowModel.SEARCH_RESULTS_TRACK_ITEM:
                View trackItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_track_item, parent);
                return new SearchResultsTrackViewHolder(trackItemView);
            case SearchResultRowModel.SEARCH_RESULTS_PLAYLIST_HEADER:
                View playlistHeaderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_playlist_header, parent);
                return new SearchResultsHeaderViewHolder(playlistHeaderView);
            case SearchResultRowModel.SEARCH_RESULTS_PLAYLIST_ITEM:
                View playlistItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_playlist_item, parent);
                return new SearchResultsPlaylistViewHolder(playlistItemView);
        }
        throw new RuntimeException("Search doesn't support this view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchResultRowModel row = rowModels.get(position);
        if(row.getHeader() != null){
            // we don't need to bind these, different layouts have the right default text
            return;
        }
        if(row.getArtist() != null){
            SearchResultsArtistViewHolder viewHolder = (SearchResultsArtistViewHolder) holder;
            viewHolder.bind(row.getArtist());
        }
        if(row.getAlbum() != null){
            SearchResultsAlbumViewHolder viewHolder = (SearchResultsAlbumViewHolder) holder;
            viewHolder.bind(row.getAlbum());
        }
        if(row.getPlaylist() != null){
            SearchResultsPlaylistViewHolder viewHolder = (SearchResultsPlaylistViewHolder) holder;
            viewHolder.bind(row.getPlaylist());
        }
        if(row.getTrack() != null){
            SearchResultsTrackViewHolder viewHolder = (SearchResultsTrackViewHolder) holder;
            viewHolder.bind(row.getTrack());
        }
    }

    public void setRowModels(List<SearchResultRowModel> searchResultRowModels){
        this.rowModels = searchResultRowModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rowModels.size();
    }
}