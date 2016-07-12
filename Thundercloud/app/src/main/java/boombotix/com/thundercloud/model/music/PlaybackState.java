package boombotix.com.thundercloud.model.music;

import java.util.ArrayList;
import java.util.List;

public class PlaybackState {
    private boolean isPlaying;
    private List<MusicListItem> queue;
    private MusicListItem currentTrack;

    public PlaybackState() {
        queue = new ArrayList<>();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public List<MusicListItem> getQueue() {
        return queue;
    }

    public void setQueue(List<MusicListItem> queue) {
        this.queue = queue;
    }

    public MusicListItem getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(MusicListItem currentTrack) {
        this.currentTrack = currentTrack;
    }

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
    public void previousTrackIfAvailible(){
        if(previousTrackAvailible()){
            this.currentTrack = queue.get((currentTrackIndex() - 1));
            this.isPlaying = false;
        }
    }

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
