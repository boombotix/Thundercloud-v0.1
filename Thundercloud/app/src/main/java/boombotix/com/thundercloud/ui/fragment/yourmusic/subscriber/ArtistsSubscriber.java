package boombotix.com.thundercloud.ui.fragment.yourmusic.subscriber;

import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import rx.Subscriber;

/**
 * Created by jsaucedo on 2/8/16.
 */
public class ArtistsSubscriber extends Subscriber<ArtistsCursorPager> {
    private ArtistsSubscriberCallback artistsSubscriberCallback;

    public ArtistsSubscriber(ArtistsSubscriberCallback artistsSubscriberCallback) {
        this.artistsSubscriberCallback = artistsSubscriberCallback;
    }

    public interface ArtistsSubscriberCallback{
        void artistsResponse(ArtistsCursorPager artistsCursorPager);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(ArtistsCursorPager artistsCursorPager) {
        artistsSubscriberCallback.artistsResponse(artistsCursorPager);
    }

}
