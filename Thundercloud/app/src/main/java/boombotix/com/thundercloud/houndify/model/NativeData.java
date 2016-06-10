
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NativeData {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("MusicCommandNativeDataKind")
    @Expose
    private String musicCommandNativeDataKind;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("SearchParameters")
    @Expose
    private SearchParameters searchParameters;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Tracks")
    @Expose
    private List<Track> tracks = new ArrayList<Track>();
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("UserRequestedAutoPlay")
    @Expose
    private boolean userRequestedAutoPlay;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("EntitySpecificationType")
    @Expose
    private String entitySpecificationType;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("OrderToDisplay")
    @Expose
    private List<String> orderToDisplay = new ArrayList<String>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The musicCommandNativeDataKind
     */
    public String getMusicCommandNativeDataKind() {
        return musicCommandNativeDataKind;
    }

    /**
     * 
     * (Required)
     * 
     * @param musicCommandNativeDataKind
     *     The MusicCommandNativeDataKind
     */
    public void setMusicCommandNativeDataKind(String musicCommandNativeDataKind) {
        this.musicCommandNativeDataKind = musicCommandNativeDataKind;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The searchParameters
     */
    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    /**
     * 
     * (Required)
     * 
     * @param searchParameters
     *     The SearchParameters
     */
    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tracks
     */
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * 
     * (Required)
     * 
     * @param tracks
     *     The Tracks
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The userRequestedAutoPlay
     */
    public boolean isUserRequestedAutoPlay() {
        return userRequestedAutoPlay;
    }

    /**
     * 
     * (Required)
     * 
     * @param userRequestedAutoPlay
     *     The UserRequestedAutoPlay
     */
    public void setUserRequestedAutoPlay(boolean userRequestedAutoPlay) {
        this.userRequestedAutoPlay = userRequestedAutoPlay;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The entitySpecificationType
     */
    public String getEntitySpecificationType() {
        return entitySpecificationType;
    }

    /**
     * 
     * (Required)
     * 
     * @param entitySpecificationType
     *     The EntitySpecificationType
     */
    public void setEntitySpecificationType(String entitySpecificationType) {
        this.entitySpecificationType = entitySpecificationType;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The orderToDisplay
     */
    public List<String> getOrderToDisplay() {
        return orderToDisplay;
    }

    /**
     * 
     * (Required)
     * 
     * @param orderToDisplay
     *     The OrderToDisplay
     */
    public void setOrderToDisplay(List<String> orderToDisplay) {
        this.orderToDisplay = orderToDisplay;
    }

}
