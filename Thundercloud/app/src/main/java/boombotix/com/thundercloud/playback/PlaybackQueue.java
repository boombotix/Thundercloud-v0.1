package boombotix.com.thundercloud.playback;

import android.support.annotation.Nullable;

import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
 * Interface defining the behavior of a queue that holds music items
 *
 * Created by kriedema on 6/14/16.
 */
public interface PlaybackQueue {
    void setQueue(List<MusicListItem> items);

    void addToQueue(MusicListItem item);

    @Nullable
    MusicListItem getCurrentTrack();

    void changeTrackToNext();

    void changeTrackToPrevious();

    void clearQueue();
}
