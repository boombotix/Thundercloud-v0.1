package boombotix.com.thundercloud.model.playback;

/**
 * Contains all info about the currently playing song on the speaker
 *
 * @author Theo Kanning
 */
public class MusicPlaybackState {

    private MusicPlaybackAction.Type action;

    private String trackId;

    private long startTime;

    private long progress;

    public MusicPlaybackState(MusicPlaybackAction.Type action, String trackId, long startTime,
            long progress) {
        this.action = action;
        this.trackId = trackId;
        this.startTime = startTime;
        this.progress = progress;
    }

    public MusicPlaybackAction.Type getAction() {
        return action;
    }

    public String getTrackId() {
        return trackId;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getProgress() {
        return progress;
    }
}
