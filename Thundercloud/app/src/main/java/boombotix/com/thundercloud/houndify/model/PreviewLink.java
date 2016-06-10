
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviewLink {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Source")
    @Expose
    private String source;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Url")
    @Expose
    private String url;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * (Required)
     * 
     * @param source
     *     The Source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * (Required)
     * 
     * @param url
     *     The Url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
