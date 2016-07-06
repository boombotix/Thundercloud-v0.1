package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("href")
    private String url;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}