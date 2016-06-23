
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MusicSearchResultsNativeData {

    @SerializedName("MusicCommandNativeDataKind")
    @Expose
    private String musicCommandNativeDataKind;

    @SerializedName("SearchParameters")
    @Expose
    private SearchParameters searchParameters;

    @SerializedName("Artists")
    @Expose
    private List<Artist> artists = new ArrayList<>();

    @SerializedName("Tracks")
    @Expose
    private List<Track> tracks = new ArrayList<Track>();

    @SerializedName("Albums")
    @Expose
    private List<Album> albums = new ArrayList<Album>();

    @SerializedName("UserRequestedAutoPlay")
    @Expose
    private boolean userRequestedAutoPlay;

    @SerializedName("EntitySpecificationType")
    @Expose
    private String entitySpecificationType;

    @SerializedName("OrderToDisplay")
    @Expose
    private List<String> orderToDisplay = new ArrayList<String>();

    /**
     *
     * @return
     *     The musicCommandNativeDataKind
     */
    public String getMusicCommandNativeDataKind() {
        return musicCommandNativeDataKind;
    }

    /**
     * 
     * @param musicCommandNativeDataKind
     *     The MusicCommandNativeDataKind
     */
    public void setMusicCommandNativeDataKind(String musicCommandNativeDataKind) {
        this.musicCommandNativeDataKind = musicCommandNativeDataKind;
    }

    /**
     * 
     * @return
     *     The searchParameters
     */
    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    /**
     * 
     * @param searchParameters
     *     The SearchParameters
     */
    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    /**
     *
     * @return
     *  The artists
     */
    public List<Artist> getArtists() {
        return artists;
    }

    /**
     *
     * @param artists
     *  The artists
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

    /**
     *
     * @return
     *     The albums
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     *
     * @param albums
     *     The Albums
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    /**
     * 
     * @return
     *     The userRequestedAutoPlay
     */
    public boolean isUserRequestedAutoPlay() {
        return userRequestedAutoPlay;
    }

    /**
     * 
     * @param userRequestedAutoPlay
     *     The UserRequestedAutoPlay
     */
    public void setUserRequestedAutoPlay(boolean userRequestedAutoPlay) {
        this.userRequestedAutoPlay = userRequestedAutoPlay;
    }

    /**
     * 
     * @return
     *     The entitySpecificationType
     */
    public String getEntitySpecificationType() {
        return entitySpecificationType;
    }

    /**
     * 
     * @param entitySpecificationType
     *     The EntitySpecificationType
     */
    public void setEntitySpecificationType(String entitySpecificationType) {
        this.entitySpecificationType = entitySpecificationType;
    }

    /**
     * 
     * @return
     *     The orderToDisplay
     */
    public List<String> getOrderToDisplay() {
        return orderToDisplay;
    }

    /**
     * 
     * @param orderToDisplay
     *     The OrderToDisplay
     */
    public void setOrderToDisplay(List<String> orderToDisplay) {
        this.orderToDisplay = orderToDisplay;
    }

}
