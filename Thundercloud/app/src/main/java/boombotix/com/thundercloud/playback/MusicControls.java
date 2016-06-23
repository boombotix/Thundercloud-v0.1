package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;
import rx.Observable;

/**
 * Interface defining the actions the UI can take on a playback queue
 *
 * Created by kriedema on 6/14/16.
 */
public interface MusicControls {
    void play();

    void next();

    void previous();

    void pause();

    void stop();

    boolean isPlaying();

    MusicListItem getCurrentTrack();

    Observable<MusicListItem> trackChangedObservable();
}
