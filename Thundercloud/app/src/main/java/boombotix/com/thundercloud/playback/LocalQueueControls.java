package boombotix.com.thundercloud.playback;

import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import boombotix.com.thundercloud.model.music.MusicListItem;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by kriedema on 6/14/16.
 */
public class LocalQueueControls implements MusicControls {

    MusicPlayerProvider provider;

    PlaybackQueue queue;
    MusicPlayer player;

    private boolean isPlaying;
    private Subject<MusicListItem, MusicListItem> subject = PublishSubject.create();

    public LocalQueueControls(PlaybackQueue queue, MusicPlayerProvider provider) {
        this.queue = queue;
        this.provider = provider;
    }

    @DebugLog
    @Override
    public void play() {
        this.player = provider.getMusicPlayer(getCurrentTrack());
        this.player.play(getCurrentTrack());
        this.isPlaying = true;
    }

    @DebugLog
    @Override
    public void next() {
        this.player.play(nextTrackFromQueue());
        this.isPlaying = true;
    }

    @DebugLog
    @Override
    public void previous() {
        this.player.play(previousTrackFromQueue());
        this.isPlaying = true;
    }

    @DebugLog
    @Override
    public void pause() {
        this.player.pause();
        this.isPlaying = false;
    }

    @DebugLog
    @Override
    public void stop() {
        this.player.stop();
        this.isPlaying = false;
    }

    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }

    @Nullable
    @Override
    public MusicListItem getCurrentTrack() {
        this.subject.onNext(this.queue.getCurrentTrack());
        return this.queue.getCurrentTrack();
    }

    @Nullable
    private MusicListItem nextTrackFromQueue(){
        this.queue.changeTrackToNext();
        this.subject.onNext(this.queue.getCurrentTrack());
        return this.queue.getCurrentTrack();
    }

    @Nullable
    private MusicListItem previousTrackFromQueue(){
        this.queue.changeTrackToPrevious();
        this.subject.onNext(this.queue.getCurrentTrack());
        return this.queue.getCurrentTrack();
    }

    @RxLogObservable
    @Override
    public Observable<MusicListItem> trackChangedObservable(){
        return subject.asObservable();
    }
}
