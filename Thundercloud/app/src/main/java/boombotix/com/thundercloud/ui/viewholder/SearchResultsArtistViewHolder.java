package boombotix.com.thundercloud.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.search.spotify.Artist;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultsArtistViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.search_artist_item_thumbnail)
    ImageView artistThumbnail;

    @Bind(R.id.search_artist_item_name)
    TextView artistName;

    Drawable placeholder;

    WeakReference<Context> contextRef;

    public SearchResultsArtistViewHolder(View itemView) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contextRef = new WeakReference<>(itemView.getContext());
        placeholder = itemView.getContext().getResources().getDrawable(R.drawable.ic_houndify);
    }

    public void bind(Artist artist){
        artistName.setText(artist.getName());
        Glide.with(contextRef.get()).load(artist.getLargestImageUrl()).placeholder(placeholder).into(artistThumbnail);
    }
}
