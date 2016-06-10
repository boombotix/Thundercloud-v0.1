
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MusicThirdPartyId {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("MusicThirdParty")
    @Expose
    private MusicThirdParty musicThirdParty;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Ids")
    @Expose
    private List<Object> ids = new ArrayList<Object>();
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("DeepLinks")
    @Expose
    private List<String> deepLinks = new ArrayList<String>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The musicThirdParty
     */
    public MusicThirdParty getMusicThirdParty() {
        return musicThirdParty;
    }

    /**
     * 
     * (Required)
     * 
     * @param musicThirdParty
     *     The MusicThirdParty
     */
    public void setMusicThirdParty(MusicThirdParty musicThirdParty) {
        this.musicThirdParty = musicThirdParty;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The ids
     */
    public List<Object> getIds() {
        return ids;
    }

    /**
     * 
     * (Required)
     * 
     * @param ids
     *     The Ids
     */
    public void setIds(List<Object> ids) {
        this.ids = ids;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The deepLinks
     */
    public List<String> getDeepLinks() {
        return deepLinks;
    }

    /**
     * 
     * (Required)
     * 
     * @param deepLinks
     *     The DeepLinks
     */
    public void setDeepLinks(List<String> deepLinks) {
        this.deepLinks = deepLinks;
    }

}
