package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.ui.viewholder.YourMusicViewHolder;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourMusicAdapter extends RecyclerView.Adapter<YourMusicViewHolder> {
    Activity activity;
    ArrayList<MusicListItem> items;
    public YourMusicAdapter(Activity activity, ArrayList<MusicListItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public YourMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //todo inflate from parent and remove call to activity
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.row_music_item, parent, false);
        return new YourMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YourMusicViewHolder holder, int position) {
        MusicListItem item = items.get(position);
        holder.bindTitle(item.getTitle());
        holder.bindSubtitle(item.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
