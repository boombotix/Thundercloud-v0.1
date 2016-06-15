package boombotix.com.thundercloud.playback;

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

    @Override
    public void play() {
        this.player =  provider.getMusicPlayer(queue.getCurrentTrack());
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void pause() {
        this.player.pause();
    }

    @Override
    public void stop() {
        this.player.stop();
    }
}
