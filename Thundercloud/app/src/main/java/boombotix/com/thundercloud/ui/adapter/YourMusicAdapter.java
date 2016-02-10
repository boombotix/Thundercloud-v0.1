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

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourMusicAdapter extends RecyclerView.Adapter<YourMusicViewHolder> {
    Activity activity;
    ArrayList<Pair<String, String>> items;
    public YourMusicAdapter(Activity activity, ArrayList<Pair<String, String>> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public YourMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.row_music_item, parent, false);
        return new YourMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YourMusicViewHolder holder, int position) {
        Pair<String, String> item = items.get(position);
        holder.bindTitle(item.first);
        holder.bindSubtitle(item.second);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
