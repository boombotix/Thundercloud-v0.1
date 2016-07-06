package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

public class Followers {
    @SerializedName("href")
    private String url;

    @SerializedName("total")
    private int total;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}