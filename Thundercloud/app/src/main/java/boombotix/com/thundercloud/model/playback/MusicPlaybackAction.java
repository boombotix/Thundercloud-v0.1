package boombotix.com.thundercloud.model.playback;

/**
 * All data required to send a playback command to the speaker
 */
public class MusicPlaybackAction {

    private Type type;

    private String trackId;

    public MusicPlaybackAction(Type type, String trackId) {
        this.type = type;
        this.trackId = trackId;
    }

    public Type getType() {
        return type;
    }

    public String getTrackId() {
        return trackId;
    }

    public enum Type {
        PLAY,
        PAUSE,
        PREVIOUS,
        SKIP
    }
}
