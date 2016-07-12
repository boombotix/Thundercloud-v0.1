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
import boombotix.com.thundercloud.model.search.spotify.Album;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultsAlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.search_album_item_artist)
    TextView artist;

    @Bind(R.id.search_album_item_name)
    TextView albumName;

    @Bind(R.id.search_album_item_thumbnail)
    ImageView albumThumbnail;

    Drawable placeholder;

    WeakReference<Context> contextRef;

    public SearchResultsAlbumViewHolder(View itemView) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contextRef = new WeakReference<>(itemView.getContext());
        placeholder = itemView.getContext().getResources().getDrawable(R.drawable.ic_houndify);
        itemView.setOnClickListener(this);
    }

    public void bind(Album album){
        artist.setText("Spotify");
        albumName.setText(album.getName());
        Glide.with(contextRef.get()).load(album.getLargestImageUrl()).placeholder(placeholder).into(albumThumbnail);
    }

    @Override
    public void onClick(View v) {

    }
}
