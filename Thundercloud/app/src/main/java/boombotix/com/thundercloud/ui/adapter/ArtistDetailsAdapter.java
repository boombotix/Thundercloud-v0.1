package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.music.MusicDetailListItem;
import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.ui.viewholder.YourMusicViewHolder;

/**
 * Created by jsaucedo on 2/17/16.
 */
public class ArtistDetailsAdapter extends RecyclerView.Adapter {
    Activity activity;
    ArrayList<MusicDetailListItem> musicDetailListItems;

    public ArtistDetailsAdapter(Activity activity, ArrayList<MusicDetailListItem> musicDetailListItems) {
        this.activity = activity;
        this.musicDetailListItems = musicDetailListItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.row_music_item, parent, false);
        return new YourMusicViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicListItem musicListItem = musicDetailListItems.get(position).musicListItem;
        ((YourMusicViewHolder) holder).bindTitle(musicListItem == null ? "null" : musicListItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return musicDetailListItems.size();
    }
}
