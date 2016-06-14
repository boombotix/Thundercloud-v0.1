package boombotix.com.thundercloud.playback;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by kriedema on 6/14/16.
 */
public class LocalPlaybackQueue implements PlaybackQueue {
    List<MusicListItem> items = new ArrayList<>();

    @Override
    public Observable<MusicListItem> getObservableQueue() {
        return Observable.from(items);
    }

    @Override
    public void setQueue(List<MusicListItem> items) {
        this.items = items;
    }

    @Override
    public void addToQueue(MusicListItem item) {
        Timber.v("Adding item to queue " + item.getTitle());
        this.items.add(item);
    }

    @Override
    public void clearQueue() {
        this.items = new ArrayList<>();
    }
}
