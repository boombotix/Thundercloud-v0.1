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
public class YourPlaylistsViewHolder extends RecyclerView.ViewHolder{
    Activity activity;
    @Bind(R.id.item_title)
    TextView itemTitle;
    @Bind(R.id.playlist_art)
    ImageView imageview;
    public YourPlaylistsViewHolder(Activity acivity, View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.activity = acivity;
    }

    public void bindTitle(String title){
        this.itemTitle.setText(title);
    }

    public void bindImage(Image image) {
        Glide
                .with(activity)
                .load(image.url)
                .fitCenter()
                .into(imageview);
    }
}
