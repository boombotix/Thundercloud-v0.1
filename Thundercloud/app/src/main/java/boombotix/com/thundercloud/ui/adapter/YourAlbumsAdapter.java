package boombotix.com.thundercloud.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.YourAlbumsViewHolder;
import boombotix.com.thundercloud.ui.viewholder.YourSongsViewHolder;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedAlbum;
import kaaes.spotify.webapi.android.models.SavedTrack;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourAlbumsAdapter extends RecyclerView.Adapter<YourAlbumsViewHolder> {
    Activity activity;
    Pager<SavedAlbum> savedAlbumPager;

    public YourAlbumsAdapter(Activity activity, Pager<SavedAlbum> savedAlbumPager) {
        this.activity = activity;
        this.savedAlbumPager = savedAlbumPager;
    }

    @Override
    public YourAlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.your_albums_item, parent, false);
        return new YourAlbumsViewHolder(activity, view);
    }

    @Override
    public void onBindViewHolder(YourAlbumsViewHolder holder, int position) {
        SavedAlbum savedAlbum = savedAlbumPager.items.get(position);
        holder.bindTitle(savedAlbum.album.name);
        ArrayList<String> artistNames = new ArrayList<>();
        for(ArtistSimple artist : savedAlbum.album.artists){
            artistNames.add(artist.name);
        }
        holder.bindArtistName(TextUtils.join(",", artistNames));
        Image image =  savedAlbum.album.images.get(0);
        holder.bindImage(image);
    }

    @Override
    public int getItemCount() {
        return savedAlbumPager.items.size();
    }
}
