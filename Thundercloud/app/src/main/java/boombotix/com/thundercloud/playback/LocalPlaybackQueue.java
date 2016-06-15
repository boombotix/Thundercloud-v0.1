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
    public static final int NO_CURRENT_TRACK = -1;

    List<MusicListItem> items = new ArrayList<>();
    int currentTrack = NO_CURRENT_TRACK;

    @Override
    public Observable<MusicListItem> getObservableQueue() {
        return Observable.from(items);
    }

    @Override
    public void setQueue(List<MusicListItem> items) {
        this.items = items;
        this.currentTrack = 0;
    }

    @Override
    public void addToQueue(MusicListItem item) {
        Timber.v("Adding item to queue " + item.getTitle());
        this.items.add(item);
        if(currentTrack == NO_CURRENT_TRACK)
            currentTrack = 0;
    }

    @Override
    public void clearQueue() {
        this.items = new ArrayList<>();
        currentTrack = NO_CURRENT_TRACK;
    }

    @Override
    public MusicListItem getCurrentTrack() {
        if(currentTrack == NO_CURRENT_TRACK) return null;

        return this.items.get(currentTrack);
    }

    @Override
    public MusicListItem getNextTrack() {
        if(currentTrack == NO_CURRENT_TRACK) return null;

        if(currentTrack + 1 <= items.size() - 1)
        {
            currentTrack++;
            return this.getCurrentTrack();
        }

        return null;
    }

    @Override
    public MusicListItem getPreviousTrack() {
        if(currentTrack == NO_CURRENT_TRACK) return null;

        if(currentTrack - 1 >= 0){
            currentTrack--;
            return this.getCurrentTrack();
        }

        return null;
    }
}
