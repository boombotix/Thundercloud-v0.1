package boombotix.com.thundercloud.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.model.music.Service;
import boombotix.com.thundercloud.model.search.spotify.Playlist;
import boombotix.com.thundercloud.playback.MusicPlayer;
import butterknife.Bind;
import butterknife.ButterKnife;



public class SearchResultsPlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.search_playlist_item_thumbnail)
    ImageView thumbnail;

    @Bind(R.id.search_playlist_item_author)
    TextView authorName;

    @Bind(R.id.search_playlist_item_name)
    TextView playlistName;

    @Inject
    MusicPlayer musicPlayer;

    Drawable placeholder;

    WeakReference<Context> contextRef;

    public SearchResultsPlaylistViewHolder(View itemView) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contextRef = new WeakReference<>(itemView.getContext());
        placeholder = itemView.getContext().getResources().getDrawable(R.drawable.ic_houndify);

        itemView.setOnClickListener(this);
        ThundercloudApplication.instance().getThundercloudGraph().inject(this);
    }

    public void bind(Playlist playlist){
        authorName.setText(playlist.getOwner().getId());
        playlistName.setText(playlist.getName());

        Glide.with(contextRef.get()).load(playlist.getLargestImageUrl()).placeholder(placeholder).into(thumbnail);

        this.itemView.setTag(playlist);
    }

    @Override
    public void onClick(View v) {
        if(v == null) return;
        if(!(v.getTag() instanceof Playlist)) return;

        Playlist playlist = (Playlist) v.getTag();

        boombotix.com.thundercloud.model.music.Playlist playlistItem = new boombotix.com.thundercloud.model.music.Playlist();
        playlistItem.setName(playlist.getName());
        playlistItem.setUri(playlist.getUri());
        playlistItem.setArtworkUrl(playlist.getLargestImageUrl());
        playlistItem.setService(Service.Spotify);

        musicPlayer.clearQueue();
        musicPlayer.addToQueue(playlistItem);

        musicPlayer.play();
    }
}