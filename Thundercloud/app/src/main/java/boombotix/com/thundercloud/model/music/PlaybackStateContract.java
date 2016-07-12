package boombotix.com.thundercloud.model.music;

import java.util.List;

public interface PlaybackStateContract {
    boolean isPlaying();

    void setPlaying(boolean playing);

    List<MusicListItem> getQueue();

    void setQueue(List<MusicListItem> queue);

    MusicListItem getCurrentTrack();

    void setCurrentTrack(MusicListItem currentTrack);

    boolean previousTrackAvailible();

    void previousTrackIfAvailible();

    boolean nextTrackAvailible();

    void nextTrackIfAvailible();
}
