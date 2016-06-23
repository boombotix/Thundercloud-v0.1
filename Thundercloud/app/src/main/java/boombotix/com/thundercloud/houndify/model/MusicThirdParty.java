
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicThirdParty {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Name")
    @Expose
    private String name;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     * @param name
     *     The Name
     */
    public void setName(String name) {
        this.name = name;
    }

}
