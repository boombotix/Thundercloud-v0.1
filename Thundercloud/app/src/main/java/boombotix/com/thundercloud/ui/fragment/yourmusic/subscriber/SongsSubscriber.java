package boombotix.com.thundercloud.ui.fragment.yourmusic.subscriber;

import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.SavedTrack;
import rx.Subscriber;

/**
 * Created by jsaucedo on 2/8/16.
 */
public class SongsSubscriber extends Subscriber<Pager<SavedTrack>> {
    private SongsSubscriberCallback songsSubscriberCallback;

    public SongsSubscriber(SongsSubscriberCallback songsSubscriberCallback) {
        this.songsSubscriberCallback = songsSubscriberCallback;
    }

    public interface SongsSubscriberCallback{
        void songsResponse(Pager<SavedTrack> savedTrackPager);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Pager<SavedTrack> savedTrackPager) {
        songsSubscriberCallback.songsResponse(savedTrackPager);
    }

}
