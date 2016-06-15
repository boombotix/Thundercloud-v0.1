package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
 * Created by kriedema on 6/14/16.
 */
public interface MusicPlayer {
    void play(MusicListItem item);

    void pause();

    void stop();

    void dispose();
}
