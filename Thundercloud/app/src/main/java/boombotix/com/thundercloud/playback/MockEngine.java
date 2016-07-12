package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;
import timber.log.Timber;

/**
 * Mock audioEngine to be used in mock and test builds
 *
 * Created by kriedema on 6/14/16.
 */
public class MockEngine implements AudioEngine {
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
