package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("url")
    private String url;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
