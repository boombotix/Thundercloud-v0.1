
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyLink {

    @SerializedName("Store")
    @Expose
    private String store;
    @SerializedName("URL")
    @Expose
    private String uRL;

    /**
     * 
     * @return
     *     The store
     */
    public String getStore() {
        return store;
    }

    /**
     * 
     * @param store
     *     The Store
     */
    public void setStore(String store) {
        this.store = store;
    }

    /**
     * 
     * @return
     *     The uRL
     */
    public String getURL() {
        return uRL;
    }

    /**
     * 
     * @param uRL
     *     The URL
     */
    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}
