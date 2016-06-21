package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;
import timber.log.Timber;

/**
 * Mock player to be used in mock and test builds
 *
 * Created by kriedema on 6/14/16.
 */
public class MockPlayer implements MusicPlayer {
    @Override
    public void play(MusicListItem item) {
        Timber.d("Playing item " + item.getTitle());
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void dispose() {

    }
}
