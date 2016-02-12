package boombotix.com.thundercloud.ui.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourSongsViewHolder extends RecyclerView.ViewHolder{
    Activity activity;
    @Bind(R.id.song_title)
    TextView songTitle;
    @Bind(R.id.artist_name)
    TextView artistName;
    @Bind(R.id.album_name)
    TextView albumName;
    @Bind(R.id.album_art)
    ImageView imageview;
    public YourSongsViewHolder(Activity acivity, View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.activity = acivity;
    }

    public void bindTitle(String title){
        this.songTitle.setText(title);
    }

    public void bindImage(Image image) {
        Glide
                .with(activity)
                .load(image.url)
                .fitCenter()
                .into(imageview);
    }

    public void bindArtistName(String artist) {
        this.artistName.setText(artist);
    }

    public void bindAlbumTitle(String name) {
        this.albumName.setText(name);
    }
}
