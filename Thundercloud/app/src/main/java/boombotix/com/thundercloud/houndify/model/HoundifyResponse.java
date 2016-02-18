package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Default response from houndify
 *
 * Created by jsaucedo on 2/16/16.
 */
public class HoundifyResponse {
    @SerializedName("Tracks")
    private List<Track> tracks;
    @SerializedName("UserRequestedAutoPlay")
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
