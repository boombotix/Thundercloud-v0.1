
package boombotix.com.thundercloud.houndify.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.*;

public class Track {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("TrackID")
    @Expose
    private long trackID;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("AlbumID")
    @Expose
    private long albumID;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("ArtistID")
    @Expose
    private long artistID;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("TrackName")
    @Expose
    private String trackName;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("AlbumName")
    @Expose
    private String albumName;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("ArtistName")
    @Expose
    private String artistName;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("AlbumDate")
    @Expose
    private String albumDate;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("AutoPlayPreview")
    @Expose
    private boolean autoPlayPreview;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("MusicThirdPartyIds")
    @Expose
    private List<MusicThirdPartyId> musicThirdPartyIds = new ArrayList<MusicThirdPartyId>();
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("PreviewLinks")
    @Expose
    private List<PreviewLink> previewLinks = new ArrayList<PreviewLink>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The trackID
     */
    public long getTrackID() {
        return trackID;
    }

    /**
     * 
     * (Required)
     * 
     * @param trackID
     *     The TrackID
     */
    public void setTrackID(long trackID) {
        this.trackID = trackID;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The albumID
     */
    public long getAlbumID() {
        return albumID;
    }

    /**
     * 
     * (Required)
     * 
     * @param albumID
     *     The AlbumID
     */
    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The artistID
     */
    public long getArtistID() {
        return artistID;
    }

    /**
     * 
     * (Required)
     * 
     * @param artistID
     *     The ArtistID
     */
    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The trackName
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * 
     * (Required)
     * 
     * @param trackName
     *     The TrackName
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The albumName
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * 
     * (Required)
     * 
     * @param albumName
     *     The AlbumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * 
     * (Required)
     * 
     * @param artistName
     *     The ArtistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The albumDate
     */
    public String getAlbumDate() {
        return albumDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param albumDate
     *     The AlbumDate
     */
    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The autoPlayPreview
     */
    public boolean isAutoPlayPreview() {
        return autoPlayPreview;
    }

    /**
     * 
     * (Required)
     * 
     * @param autoPlayPreview
     *     The AutoPlayPreview
     */
    public void setAutoPlayPreview(boolean autoPlayPreview) {
        this.autoPlayPreview = autoPlayPreview;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The musicThirdPartyIds
     */
    public List<MusicThirdPartyId> getMusicThirdPartyIds() {
        return musicThirdPartyIds;
    }

    /**
     * 
     * (Required)
     * 
     * @param musicThirdPartyIds
     *     The MusicThirdPartyIds
     */
    public void setMusicThirdPartyIds(List<MusicThirdPartyId> musicThirdPartyIds) {
        this.musicThirdPartyIds = musicThirdPartyIds;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The previewLinks
     */
    public List<PreviewLink> getPreviewLinks() {
        return previewLinks;
    }

    /**
     * 
     * (Required)
     * 
     * @param previewLinks
     *     The PreviewLinks
     */
    public void setPreviewLinks(List<PreviewLink> previewLinks) {
        this.previewLinks = previewLinks;
    }

    public MusicListItem convertToListItem(){
        Song song = new Song();

        song.setName(this.trackName);
        song.setAlbum(this.albumName);
        song.setArtist(this.artistName);
        song.setService(Service.Spotify);
        song.setUri(this.getSpotifyId());

        return song;
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
