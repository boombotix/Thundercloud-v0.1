package boombotix.com.thundercloud.ui.fragment.yourmusic.subscriber;

import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.SavedAlbum;
import kaaes.spotify.webapi.android.models.SavedTrack;
import rx.Subscriber;

/**
 * Created by jsaucedo on 2/8/16.
 */
public class AlbumsSubscriber extends Subscriber<Pager<SavedAlbum>> {
    private AlbumsSubscriberCallback albumsSubscriberCallback;

    public AlbumsSubscriber(AlbumsSubscriberCallback albumsSubscriberCallback) {
        this.albumsSubscriberCallback = albumsSubscriberCallback;
    }

    public interface AlbumsSubscriberCallback{
        void albumsResponse(Pager<SavedAlbum> savedAlbumPager);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Pager<SavedAlbum> savedAlbumPager) {
        albumsSubscriberCallback.albumsResponse(savedAlbumPager);
    }

}
