package boombotix.com.thundercloud.music;

import java.util.ArrayList;
import java.util.List;

/**
 * Gives public methods for controlling music playback, and alerts listeners when queue, state, or
 * song changes are made
 *
 * @author Theo Kanning
 */
public class MusicController {

    private MusicQueue musicQueue;

    private MusicPlayer musicPlayer;

    private List<MusicPlaybackListener> musicPlaybackListeners = new ArrayList<>();

    private List<QueueChangeListener> queueChangeListeners = new ArrayList<>();

    private List<SongProgressListener> songProgressListeners = new ArrayList<>();

    public MusicController(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
        musicPlayer.registerSongFinishedListener(songFinishedListener);
    }

    public void play() {
        musicPlayer.play();
        notifyPlayListeners();
    }

    public void pause() {
        musicPlayer.pause();
        notifyPauseListeners();
    }

    public void skip() {
        playNextSong();
    }

    public void addToQueue() {
        //todo separate methods for song/list<song>
    }

    public void removeFromQueue() {

    }

    public void replaceQueue() {

    }

    private void playNextSong() {
        //todo remove first song from queue
        musicPlayer.playSong();
        notifySongChangeListeners();
    }

    private MusicPlayer.SongFinishedListener songFinishedListener = () -> playNextSong();

    public void registerMusicPlaybackListener(MusicPlaybackListener listener) {
        if (!musicPlaybackListeners.contains(listener)) {
            musicPlaybackListeners.add(listener);
        }
    }

    public void unregisterMusicPlaybackListener(MusicPlaybackListener listener) {
        musicPlaybackListeners.remove(listener);
    }

    public void registerQueueChangeListener(QueueChangeListener listener) {
        if (!queueChangeListeners.contains(listener)) {
            queueChangeListeners.add(listener);
        }
    }

    public void unregisterQueueChangeListener(QueueChangeListener listener) {
        queueChangeListeners.remove(listener);
    }

    public void registerSongProgressListener(SongProgressListener listener) {
        if (!songProgressListeners.contains(listener)) {
            songProgressListeners.add(listener);
        }
    }

    public void unregisterSongProgressListener(SongProgressListener listener) {
        songProgressListeners.remove(listener);
    }

    private void notifyPlayListeners() {
        for (MusicPlaybackListener listener : musicPlaybackListeners) {
            listener.onPlay();
        }
    }

    private void notifyPauseListeners() {
        for (MusicPlaybackListener listener : musicPlaybackListeners) {
            listener.onPause();
        }
    }

    private void notifySongChangeListeners() {
        //todo add song model
        for (MusicPlaybackListener listener : musicPlaybackListeners) {
            listener.onSongChange();
        }
    }

    private void notifyQueueChangeListeners() {
        for (QueueChangeListener listener : queueChangeListeners) {
            listener.onQueueChanged();
        }
    }

    private void notifySongProgressListeners(long progress) {
        for (SongProgressListener listener : songProgressListeners) {
            listener.onSongProgressUpdated(progress);
        }
    }

}
