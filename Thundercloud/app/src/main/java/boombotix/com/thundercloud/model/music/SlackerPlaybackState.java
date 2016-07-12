package boombotix.com.thundercloud.model.music;

/**
 * Should handle managing the limited skip counts for free slacker users
 */
public class SlackerPlaybackState extends BasicPlaybackState {
    @Override
    public boolean previousTrackAvailible() {
        return super.previousTrackAvailible();
    }

    @Override
    public void previousTrackIfAvailible() {
        super.previousTrackIfAvailible();
    }

    @Override
    public boolean nextTrackAvailible() {
        return super.nextTrackAvailible();
    }

    @Override
    public void nextTrackIfAvailible() {
        super.nextTrackIfAvailible();
    }
}
