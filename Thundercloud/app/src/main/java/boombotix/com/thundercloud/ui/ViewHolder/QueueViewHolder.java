package boombotix.com.thundercloud.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Row for a song in the music queue.
 *
 * @author Theo Kanning
 */
public class QueueViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.song)
    TextView name;

    @Bind(R.id.artist)
    TextView artist;

    public QueueViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setArtist(String artist) {
        this.artist.setText(artist);
    }
}
