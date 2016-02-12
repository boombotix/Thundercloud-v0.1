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
public class YourArtistsViewHolder extends RecyclerView.ViewHolder{
    Activity activity;
    @Bind(R.id.artist_name)
    TextView artistName;
    @Bind(R.id.artist_img)
    ImageView imageview;
    public YourArtistsViewHolder(Activity acivity, View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.activity = acivity;
    }

    public void bindName(String name){
        this.artistName.setText(name);
    }

    public void bindImage(Image image) {
        Glide
                .with(activity)
                .load(image.url)
                .fitCenter()
                .into(imageview);
    }
}
