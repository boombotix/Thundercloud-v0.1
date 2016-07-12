package boombotix.com.thundercloud.model.music;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO to represent the playback state for a basic music item
 */
public class BasicPlaybackState implements PlaybackStateContract {
    private boolean isPlaying;
    private List<MusicListItem> queue;
    private MusicListItem currentTrack;

    public BasicPlaybackState() {
        queue = new ArrayList<>();
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public List<MusicListItem> getQueue() {
        return queue;
    }

    @Override
    public void setQueue(List<MusicListItem> queue) {
        this.queue = queue;
    }

    @Override
    public MusicListItem getCurrentTrack() {
        return currentTrack;
    }

    @Override
    public void setCurrentTrack(MusicListItem currentTrack) {
        this.currentTrack = currentTrack;
    }

    @Override
    public boolean previousTrackAvailible(){
        int indexOfNextTrack = currentTrackIndex() - 1;

        //noinspection RedundantIfStatement
        if(indexOfNextTrack < 0 || indexOfNextTrack >= queue.size()){
            return false;
        }

        return true;
    }

    /**
     * Changes the current track to the previous one in the list if won't throw an IndexOutOfBounds exception
     *
     * The caller is responsible for playing the track and updating the state here
     */
    @Override
    public void previousTrackIfAvailible(){
        if(previousTrackAvailible()){
            this.currentTrack = queue.get((currentTrackIndex() - 1));
            this.isPlaying = false;
        }
    }

    @Override
    public boolean nextTrackAvailible(){
        int indexOfNextTrack = currentTrackIndex() + 1;

        //noinspection RedundantIfStatement
        if(indexOfNextTrack < 0 || indexOfNextTrack >= queue.size()){
            return false;
        }

        return true;
    }

    /**
     * Changes the current track to the next one in the list if won't throw an IndexOutOfBounds exception
     *
     * The caller is responsible for playing the track and updating the state here
     */
    @Override
    public void nextTrackIfAvailible(){
        if(nextTrackAvailible()){
            this.currentTrack = queue.get((currentTrackIndex() + 1));
            this.isPlaying = false;
        }
    }

    private int currentTrackIndex(){
        return queue.indexOf(currentTrack);
    }
}
