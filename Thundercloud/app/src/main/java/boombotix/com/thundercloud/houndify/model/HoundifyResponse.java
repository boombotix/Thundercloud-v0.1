package boombotix.com.thundercloud.houndify.model;

import java.util.List;

/**
 * Default response from houndify
 *
 * Created by jsaucedo on 2/16/16.
 */
public class HoundifyResponse {
    private List<Track> tracks;
    private boolean userRequestedAutoPlay;

    public boolean isUserRequestedAutoPlay() {
        return userRequestedAutoPlay;
    }

    public void setUserRequestedAutoPlay(boolean userRequestedAutoPlay) {
        this.userRequestedAutoPlay = userRequestedAutoPlay;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }


}
