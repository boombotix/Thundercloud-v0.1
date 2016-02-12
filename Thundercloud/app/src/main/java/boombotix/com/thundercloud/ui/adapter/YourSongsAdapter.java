package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.YourPlaylistsViewHolder;
import boombotix.com.thundercloud.ui.viewholder.YourSongsViewHolder;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.SavedTrack;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourSongsAdapter extends RecyclerView.Adapter<YourSongsViewHolder> {
    Activity activity;
    Pager<SavedTrack> savedTrackPager;

    public YourSongsAdapter(Activity activity, Pager<SavedTrack> savedTrackPager) {
        this.activity = activity;
        this.savedTrackPager = savedTrackPager;
    }

    @Override
    public YourSongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.your_songs_item, parent, false);
        return new YourSongsViewHolder(activity, view);
    }

    @Override
    public void onBindViewHolder(YourSongsViewHolder holder, int position) {
        SavedTrack savedTrack = savedTrackPager.items.get(position);
        holder.bindTitle(savedTrack.track.name);
        ArrayList<String> artistNames = new ArrayList<>();
        for(ArtistSimple artist : savedTrack.track.artists){
            artistNames.add(artist.name);
        }
        holder.bindArtistName(TextUtils.join(",", artistNames));
        holder.bindAlbumTitle(savedTrack.track.album.name);
        Image image =  savedTrack.track.album.images.get(0);
        holder.bindImage(image);
    }

    @Override
    public int getItemCount() {
        return savedTrackPager.items.size();
    }
}
