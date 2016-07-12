package boombotix.com.thundercloud.playback;

import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.music.PlaybackState;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * MusicPlayer to handle basic playback functionality
 *
 * Created by kriedema on 6/14/16.
 */
public class BaseMusicPlayer implements MusicPlayer {

    AudioEngineProvider provider;

    AudioEngine audioEngine;

    private Subject<PlaybackState, PlaybackState> subject = PublishSubject.create();
    private PlaybackState playbackState;

    public BaseMusicPlayer(AudioEngineProvider provider) {
        this.provider = provider;
        playbackState = new PlaybackState();
    }

    @DebugLog
    @Override
    public void play() {
        this.audioEngine = provider.getMusicPlayer(getCurrentTrack());
        this.audioEngine.play(getCurrentTrack());
        this.playbackState.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void next() {
        if(!this.playbackState.isPlaying()){
            this.play();
            return;
        }

        this.audioEngine.play(nextTrackFromQueue());
        this.playbackState.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void previous() {
        this.audioEngine.play(previousTrackFromQueue());
        this.playbackState.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void pause() {
        this.audioEngine.pause();
        this.playbackState.setPlaying(false);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void stop() {
        this.audioEngine.stop();
        this.playbackState.setPlaying(false);
        this.publishStateChange();
    }

    @Override
    public boolean isPlaying() {
        return this.playbackState.isPlaying();
    }

    @Nullable
    @Override
    public MusicListItem getCurrentTrack() {
        return this.playbackState.getCurrentTrack();
    }

    @Nullable
    private MusicListItem nextTrackFromQueue(){
        if(!this.playbackState.nextTrackAvailible()){
            return null;
        }

        this.playbackState.nextTrackIfAvailible();

        return this.playbackState.getCurrentTrack();
    }

    @Nullable
    private MusicListItem previousTrackFromQueue(){
        if(!this.playbackState.previousTrackAvailible()){
            return null;
        }

        this.playbackState.previousTrackIfAvailible();

        return this.playbackState.getCurrentTrack();
    }

    @Override
    public void clearQueue() {
        this.playbackState.setCurrentTrack(null);
        this.playbackState.setQueue(new ArrayList<>());
    }

    @Override
    public void setQueue(List<MusicListItem> queue) {
        this.playbackState.setCurrentTrack(null);
        this.playbackState.setQueue(queue);

        if(queue.size() != 0){
            this.playbackState.setCurrentTrack(queue.get(0));
        }
    }

    @Override
    public void addToQueue(MusicListItem musicListItem) {
        this.playbackState.getQueue().add(musicListItem);

        if(this.playbackState.getCurrentTrack() == null){
            this.playbackState.setCurrentTrack(musicListItem);
        }
    }

    @RxLogObservable
    @Override
    public Observable<PlaybackState> stateChangedObservable(){
        return subject.asObservable();
    }

    private void publishStateChange(){
        this.subject.onNext(this.playbackState);
    }
}
