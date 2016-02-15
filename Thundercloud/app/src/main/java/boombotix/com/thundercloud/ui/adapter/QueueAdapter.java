package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.QueueViewHolder;

/**
 * Adapter for the song queue. Shows the first item with a different layout resource.
 *
 * @author Theo Kanning
 */
public class QueueAdapter extends RecyclerView.Adapter<QueueViewHolder> {

    private static final int TYPE_FIRST_SONG = 0;

    private static final int TYPE_DEFAULT = 1;

    List<Pair<String, String>> songs;

    public QueueAdapter(List<Pair<String, String>> songs) {
        this.songs = songs;
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FIRST_SONG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_queue_first, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_queue, parent, false);
        }
        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        Pair<String, String> song = songs.get(position);
        holder.setName(song.first);
        holder.setArtist(song.second);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_SONG;
        } else {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
