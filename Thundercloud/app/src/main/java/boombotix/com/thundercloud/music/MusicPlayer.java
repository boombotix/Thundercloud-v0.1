package boombotix.com.thundercloud.music;

/**
 * @author Theo Kanning
 */
public class MusicPlayer {

    private SongFinishedListener listener;

    void play(){}
    void pause(){}
    void playSong(){}

    void registerSongFinishedListener(SongFinishedListener listener){
        this.listener = listener;
    }

    interface SongFinishedListener{
        void onSongFinished();
    }
}
