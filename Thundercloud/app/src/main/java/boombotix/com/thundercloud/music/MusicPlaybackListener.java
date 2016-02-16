package boombotix.com.thundercloud.music;

/**
 * @author Theo Kanning
 */
public interface MusicPlaybackListener {
    void onPause();
    void onPlay();
    void onSongChange(); //todo add song model object
}
