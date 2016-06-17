package boombotix.com.thundercloud.playback;

import hugo.weaving.DebugLog;

/**
 * Created by kriedema on 6/14/16.
 */
public class LocalQueueControls implements MusicControls {

    MusicPlayerProvider provider;

    PlaybackQueue queue;
    MusicPlayer player;

    public LocalQueueControls(PlaybackQueue queue, MusicPlayerProvider provider) {
        this.queue = queue;
        this.provider = provider;
    }

    @DebugLog
    @Override
    public void play() {
        this.player =  provider.getMusicPlayer(queue.getCurrentTrack());
        this.player.play(queue.getCurrentTrack());
    }

    @DebugLog
    @Override
    public void next() {

    }

    @DebugLog
    @Override
    public void previous() {

    }

    @DebugLog
    @Override
    public void pause() {
        this.player.pause();
    }

    @DebugLog
    @Override
    public void stop() {
        this.player.stop();
    }
}
