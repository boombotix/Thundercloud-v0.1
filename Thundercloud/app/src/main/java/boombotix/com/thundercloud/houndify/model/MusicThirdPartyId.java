
package boombotix.com.thundercloud.houndify.model;

import android.support.annotation.Nullable;

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
    private List<String> ids = new ArrayList<String>();
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
    public List<String> getIds() {
        return ids;
    }

    /**
     * 
     * (Required)
     * 
     * @param ids
     *     The Ids
     */
    public void setIds(List<String> ids) {
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

    public boolean isSpotifyId(){
        return this.musicThirdParty.getName().equals(HoundifyTypes.ThirdPartyMusicServices.Spotify.toString());
    }

    @Nullable
    public String spotifyId(){
        if(ids.size() != 0)
            return ids.get(0);
        return null;
    }

    @Nullable
    public String spotifyDeepLink(){
        if(deepLinks.size() != 0)
            return deepLinks.get(0);
        return null;
    }
}
