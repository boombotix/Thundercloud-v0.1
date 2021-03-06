
package boombotix.com.thundercloud.houndify.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.model.music.MusicListItem;
import boombotix.com.thundercloud.model.music.Service;

public class Album {

    @SerializedName("AlbumID")
    @Expose
    private long albumID;
    @SerializedName("AlbumName")
    @Expose
    private String albumName;
    @SerializedName("AlbumDate")
    @Expose
    private String albumDate;
    @SerializedName("ArtistName")
    @Expose
    private String artistName;
    @SerializedName("ArtistID")
    @Expose
    private long artistID;
    @SerializedName("MusicThirdPartyIds")
    @Expose
    private List<MusicThirdPartyId> musicThirdPartyIds = new ArrayList<MusicThirdPartyId>();
    @SerializedName("Artists")
    @Expose
    private List<Artist> artists = new ArrayList<Artist>();
    @SerializedName("Tracks")
    @Expose
    private List<Track> tracks = new ArrayList<Track>();

    /**
     * 
     * @return
     *     The albumID
     */
    public long getAlbumID() {
        return albumID;
    }

    /**
     * 
     * @param albumID
     *     The AlbumID
     */
    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    /**
     * 
     * @return
     *     The albumName
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * 
     * @param albumName
     *     The AlbumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * 
     * @return
     *     The albumDate
     */
    public String getAlbumDate() {
        return albumDate;
    }

    /**
     * 
     * @param albumDate
     *     The AlbumDate
     */
    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
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
     *     The musicThirdPartyIds
     */
    public List<MusicThirdPartyId> getMusicThirdPartyIds() {
        return musicThirdPartyIds;
    }

    /**
     * 
     * @param musicThirdPartyIds
     *     The MusicThirdPartyIds
     */
    public void setMusicThirdPartyIds(List<MusicThirdPartyId> musicThirdPartyIds) {
        this.musicThirdPartyIds = musicThirdPartyIds;
    }

    /**
     * 
     * @return
     *     The artists
     */
    public List<Artist> getArtists() {
        return artists;
    }

    /**
     * 
     * @param artists
     *     The Artists
     */
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    /**
     * 
     * @return
     *     The tracks
     */
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * 
     * @param tracks
     *     The Tracks
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public MusicListItem convertToListItem(){
        boombotix.com.thundercloud.model.music.Album album = new boombotix.com.thundercloud.model.music.Album();

        album.setName(this.albumName);
        album.setArtist(this.artistName);
        album.setService(Service.Spotify);
        album.setUri(this.getSpotifyDeepLink());

        return album;
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
