
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
    private NativeData nativeData;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The nativeData
     */
    public NativeData getNativeData() {
        return nativeData;
    }

    /**
     * 
     * (Required)
     * 
     * @param nativeData
     *     The NativeData
     */
    public void setNativeData(NativeData nativeData) {
        this.nativeData = nativeData;
    }

}
