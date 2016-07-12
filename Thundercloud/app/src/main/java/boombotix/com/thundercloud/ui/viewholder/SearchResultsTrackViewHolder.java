package boombotix.com.thundercloud.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.model.music.Service;
import boombotix.com.thundercloud.model.music.Song;
import boombotix.com.thundercloud.model.search.spotify.Track;
import boombotix.com.thundercloud.playback.MusicPlayer;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchResultsTrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.search_track_item_artist)
    TextView artist;

    @Bind(R.id.search_track_item_name)
    TextView name;

    @Bind(R.id.search_track_item_album)
    TextView album;

    @Inject
    MusicPlayer musicPlayer;

    public SearchResultsTrackViewHolder(View itemView) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemView.setOnClickListener(this);
        ThundercloudApplication.instance().getThundercloudGraph().inject(this);
    }

    public void bind(Track track){
        artist.setText(track.getArtists().get(0).getName());

        name.setText(track.getName());

        album.setText(track.getAlbum().getName());

        this.itemView.setTag(track);
    }

    @Override
    public void onClick(View v) {
        if(v == null) return;
        if(!(v.getTag() instanceof Track)) return;

        Track track = (Track) v.getTag();

        Song song = new Song();
        song.setName(track.getName());
        song.setUri(track.getUri());
        song.setService(Service.Spotify);
        song.setArtist(track.getArtists().get(0).getName());
        song.setAlbum(track.getAlbum().getName());
        song.setArtworkUrl(track.getAlbum().getLargestImageUrl());

        musicPlayer.clearQueue();
        musicPlayer.addToQueue(song);

        musicPlayer.play();
    }
}
