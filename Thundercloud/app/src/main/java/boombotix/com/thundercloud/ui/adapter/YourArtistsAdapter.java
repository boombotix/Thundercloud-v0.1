package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.YourArtistsViewHolder;
import boombotix.com.thundercloud.ui.viewholder.YourSongsViewHolder;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedTrack;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourArtistsAdapter extends RecyclerView.Adapter<YourArtistsViewHolder> {
    Activity activity;
    ArtistsCursorPager artistsCursorPager;

    public YourArtistsAdapter(Activity activity, ArtistsCursorPager artistsCursorPager) {
        this.activity = activity;
        this.artistsCursorPager = artistsCursorPager;
    }

    @Override
    public YourArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.your_artists_item, parent, false);
        return new YourArtistsViewHolder(activity, view);
    }

    @Override
    public void onBindViewHolder(YourArtistsViewHolder holder, int position) {
        Artist artist = artistsCursorPager.artists.items.get(position);
        holder.bindName(artist.name);
        Image image =  artist.images.get(0);
        holder.bindImage(image);
    }

    @Override
    public int getItemCount() {
        return artistsCursorPager.artists.items.size();
    }
}
