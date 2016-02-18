package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Track from houndify
 *
 * Created by jsaucedo on 2/16/16.
 */
public class Track {
    @SerializedName("TrackID")
    private long trackId;
    @SerializedName("AlbumID")
    private long albumId;
    @SerializedName("ArtistID")
    private long artistId;
    @SerializedName("TrackName")
    private String trackName;
    @SerializedName("AlbumName")
    private String albumName;
    @SerializedName("ArtistName")
    private String artistName;
    @SerializedName("AlbumDate")
    private String albumDate;

    public long getTrackID() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }

}
