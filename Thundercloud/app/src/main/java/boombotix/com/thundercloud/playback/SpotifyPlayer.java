package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
 * Created by kriedema on 6/14/16.
 */
public class SpotifyPlayer implements MusicPlayer {
    MusicListItem item;

    @Override
    public void play(MusicListItem item) {
        this.item = item;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        item = null;
    }

    @Override
    public void dispose() {

    }
}
