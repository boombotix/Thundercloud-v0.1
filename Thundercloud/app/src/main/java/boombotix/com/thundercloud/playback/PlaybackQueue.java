package boombotix.com.thundercloud.playback;

import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import rx.Observable;

/**
 * Created by kriedema on 6/14/16.
 */
public interface PlaybackQueue {
    Observable<MusicListItem> getObservableQueue();

    void setQueue(List<MusicListItem> items);

    void addToQueue(MusicListItem item);

    void clearQueue();
}
