package boombotix.com.thundercloud.ui.fragment.yourmusic.subscriber;

import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import rx.Subscriber;

/**
 * Created by jsaucedo on 2/8/16.
 */
public class PlaylistSubscriber extends Subscriber<Pager<PlaylistSimple>> {
    private PlaylistSubscriberCallback playlistSubscriberCallback;

    public PlaylistSubscriber(PlaylistSubscriberCallback playlistSubscriberCallback) {
        this.playlistSubscriberCallback = playlistSubscriberCallback;
    }

    public interface PlaylistSubscriberCallback{
        void playlistResponse(Pager<PlaylistSimple> playlistSimplePager);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Pager<PlaylistSimple> playlistSimplePager) {
        playlistSubscriberCallback.playlistResponse(playlistSimplePager);
    }

}
