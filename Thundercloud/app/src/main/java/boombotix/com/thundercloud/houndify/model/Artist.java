
package boombotix.com.thundercloud.houndify.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.music.Service;

public class Artist {

    @SerializedName("ArtistID")
    @Expose
    private long artistID;
    @SerializedName("ArtistName")
    @Expose
    private String artistName;
    @SerializedName("ArtistType")
    @Expose
    private String artistType;
    @SerializedName("MusicThirdPartyIds")
    @Expose
    private List<MusicThirdPartyId> musicThirdPartyIds = new ArrayList<>();
    @SerializedName("TracksLabel")
    @Expose
    private String tracksLabel;
    @SerializedName("Tracks")
    @Expose
    private List<Track> tracks = new ArrayList<>();

    /**
     * 
     * @return
     *     The artistID
     */
    public long getArtistID() {
        return artistID;
    }

    /**
     * 
     * @param artistID
     *     The ArtistID
     */
    public void setArtistID(long artistID) {
        this.artistID = artistID;
    }

    /**
     * 
     * @return
     *     The artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * 
     * @param artistName
     *     The ArtistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * 
     * @return
     *     The artistType
     */
    public String getArtistType() {
        return artistType;
    }

    /**
     * 
     * @param artistType
     *     The ArtistType
     */
    public void setArtistType(String artistType) {
        this.artistType = artistType;
    }

    public List<MusicThirdPartyId> getMusicThirdPartyIds() {
        return musicThirdPartyIds;
    }

    public void setMusicThirdPartyIds(List<MusicThirdPartyId> musicThirdPartyIds) {
        this.musicThirdPartyIds = musicThirdPartyIds;
    }

    public String getTracksLabel() {
        return tracksLabel;
    }

    public void setTracksLabel(String tracksLabel) {
        this.tracksLabel = tracksLabel;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public MusicListItem convertToListItem(){
        boombotix.com.thundercloud.model.music.Artist artist = new boombotix.com.thundercloud.model.music.Artist();

        artist.setName(this.artistName);
        artist.setService(Service.Spotify);
        artist.setUri(this.getSpotifyDeepLink());

        return artist;
    }

    @Nullable
    public String getSpotifyId() {
        for (MusicThirdPartyId thirdPartyId : this.musicThirdPartyIds) {
            if (thirdPartyId.isSpotifyId())
                return thirdPartyId.spotifyId();
        }

        return null;
    }

    @Nullable
    public String getSpotifyDeepLink() {
        for (MusicThirdPartyId thirdPartyId : this.musicThirdPartyIds) {
            if (thirdPartyId.isSpotifyId())
                return thirdPartyId.spotifyDeepLink();
        }

        return null;
    }
}
