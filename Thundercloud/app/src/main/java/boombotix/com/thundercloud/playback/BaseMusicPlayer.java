package boombotix.com.thundercloud.playback;

import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.music.BasicPlaybackState;
import boombotix.com.thundercloud.model.music.PlaybackStateContract;
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

    private Subject<PlaybackStateContract, PlaybackStateContract> subject = PublishSubject.create();
    private PlaybackStateContract playbackStateContract;

    public BaseMusicPlayer(AudioEngineProvider provider) {
        this.provider = provider;
        playbackStateContract = new BasicPlaybackState();
    }

    @DebugLog
    @Override
    public void play() {
        this.audioEngine = provider.getMusicPlayer(getCurrentTrack());
        this.audioEngine.play(getCurrentTrack());
        this.playbackStateContract.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void next() {
        if(!this.playbackStateContract.isPlaying()){
            this.play();
            return;
        }

        this.audioEngine.play(nextTrackFromQueue());
        this.playbackStateContract.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void previous() {
        this.audioEngine.play(previousTrackFromQueue());
        this.playbackStateContract.setPlaying(true);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void pause() {
        this.audioEngine.pause();
        this.playbackStateContract.setPlaying(false);
        this.publishStateChange();
    }

    @DebugLog
    @Override
    public void stop() {
        this.audioEngine.stop();
        this.playbackStateContract.setPlaying(false);
        this.publishStateChange();
    }

    @Override
    public boolean isPlaying() {
        return this.playbackStateContract.isPlaying();
    }

    @Nullable
    @Override
    public MusicListItem getCurrentTrack() {
        return this.playbackStateContract.getCurrentTrack();
    }

    @Nullable
    private MusicListItem nextTrackFromQueue(){
        if(!this.playbackStateContract.nextTrackAvailible()){
            return null;
        }

        this.playbackStateContract.nextTrackIfAvailible();

        return this.playbackStateContract.getCurrentTrack();
    }

    @Nullable
    private MusicListItem previousTrackFromQueue(){
        if(!this.playbackStateContract.previousTrackAvailible()){
            return null;
        }

        this.playbackStateContract.previousTrackIfAvailible();

        return this.playbackStateContract.getCurrentTrack();
    }

    @Override
    public void clearQueue() {
        this.playbackStateContract.setCurrentTrack(null);
        this.playbackStateContract.setQueue(new ArrayList<>());
    }

    @Override
    public void setQueue(List<MusicListItem> queue) {
        this.playbackStateContract.setCurrentTrack(null);
        this.playbackStateContract.setQueue(queue);

        if(queue.size() != 0){
            this.playbackStateContract.setCurrentTrack(queue.get(0));
        }
    }

    @Override
    public void addToQueue(MusicListItem musicListItem) {
        this.playbackStateContract.getQueue().add(musicListItem);

        if(this.playbackStateContract.getCurrentTrack() == null){
            this.playbackStateContract.setCurrentTrack(musicListItem);
        }
    }

    @RxLogObservable
    @Override
    public Observable<PlaybackStateContract> stateChangedObservable(){
        return subject.asObservable();
    }

    private void publishStateChange(){
        this.subject.onNext(this.playbackStateContract);
    }
}
