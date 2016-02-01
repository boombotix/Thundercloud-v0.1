package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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
    ArrayList<Object> items;
    public YourMusicAdapter(Activity activity, ArrayList<Object> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public YourMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.music_list_item, parent, false);
        return new YourMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YourMusicViewHolder holder, int position) {
        String item = (String) items.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
