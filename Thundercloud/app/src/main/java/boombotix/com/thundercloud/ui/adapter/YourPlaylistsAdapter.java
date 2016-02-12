package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.YourMusicViewHolder;
import boombotix.com.thundercloud.ui.viewholder.YourPlaylistsViewHolder;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourPlaylistsAdapter extends RecyclerView.Adapter<YourPlaylistsViewHolder> {
    Activity activity;
    Pager<PlaylistSimple> playlistSimplePager;

    public YourPlaylistsAdapter(Activity activity, Pager<PlaylistSimple> playlistSimplePager) {
        this.activity = activity;
        this.playlistSimplePager = playlistSimplePager;
    }

    @Override
    public YourPlaylistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.your_playlists_item, parent, false);
        return new YourPlaylistsViewHolder(activity, view);
    }

    @Override
    public void onBindViewHolder(YourPlaylistsViewHolder holder, int position) {
        PlaylistSimple playlistSimple = playlistSimplePager.items.get(position);
        holder.bindTitle(playlistSimple.name);
        Image image =  playlistSimple.images.get(0);
        holder.bindImage(image);
    }

    @Override
    public int getItemCount() {
        return playlistSimplePager.items.size();
    }
}
