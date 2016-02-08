package boombotix.com.thundercloud.ui.fragment.yourmusic.subscriber;

import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import rx.Subscriber;

/**
 * Created by jsaucedo on 2/8/16.
 */
public class PlaylistsSubscriber extends Subscriber<Pager<PlaylistSimple>> {
    private PlaylistsSubscriberCallback playlistsSubscriberCallback;

    public PlaylistsSubscriber(PlaylistsSubscriberCallback playlistsSubscriberCallback) {
        this.playlistsSubscriberCallback = playlistsSubscriberCallback;
    }

    public interface PlaylistsSubscriberCallback{
        void playlistsResponse(Pager<PlaylistSimple> playlistsSimplePager);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Pager<PlaylistSimple> playlistsSimplePager) {
        playlistsSubscriberCallback.playlistsResponse(playlistsSimplePager);
    }

}
