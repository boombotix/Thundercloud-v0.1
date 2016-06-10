
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchParameters {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("SearchCriteriaType")
    @Expose
    private String searchCriteriaType;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("MusicSearchTargetType")
    @Expose
    private String musicSearchTargetType;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The searchCriteriaType
     */
    public String getSearchCriteriaType() {
        return searchCriteriaType;
    }

    /**
     * 
     * (Required)
     * 
     * @param searchCriteriaType
     *     The SearchCriteriaType
     */
    public void setSearchCriteriaType(String searchCriteriaType) {
        this.searchCriteriaType = searchCriteriaType;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The musicSearchTargetType
     */
    public String getMusicSearchTargetType() {
        return musicSearchTargetType;
    }

    /**
     * 
     * (Required)
     * 
     * @param musicSearchTargetType
     *     The MusicSearchTargetType
     */
    public void setMusicSearchTargetType(String musicSearchTargetType) {
        this.musicSearchTargetType = musicSearchTargetType;
    }

}
