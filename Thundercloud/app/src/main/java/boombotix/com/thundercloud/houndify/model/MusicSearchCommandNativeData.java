
package boombotix.com.thundercloud.houndify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicSearchCommandNativeData {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("NativeData")
    @Expose
    private MusicSearchResultsNativeData MusicSearchResultsNativeData;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The nativeData
     */
    public MusicSearchResultsNativeData getMusicSearchResultsNativeData() {
        return MusicSearchResultsNativeData;
    }

    /**
     * 
     * (Required)
     * 
     * @param MusicSearchResultsNativeData
     *     The MusicSearchResultsNativeData
     */
    public void setMusicSearchResultsNativeData(MusicSearchResultsNativeData MusicSearchResultsNativeData) {
        this.MusicSearchResultsNativeData = MusicSearchResultsNativeData;
    }

}
