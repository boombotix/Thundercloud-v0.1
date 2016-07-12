package boombotix.com.thundercloud.playback;

import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.music.PlaybackStateContract;
import rx.Observable;

/**
 * Interface defining the actions the UI can take on a playback queue
 *
 * Created by kriedema on 6/14/16.
 */
public interface MusicPlayer {
    void play();

    void next();

    void previous();

    void pause();

    void stop();

    boolean isPlaying();

    MusicListItem getCurrentTrack();

    void clearQueue();

    void setQueue(List<MusicListItem> queue);

    void addToQueue(MusicListItem musicListItem);

    Observable<PlaybackStateContract> stateChangedObservable();
}
