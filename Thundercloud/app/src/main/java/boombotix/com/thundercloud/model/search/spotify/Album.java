package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {
    @SerializedName("album_type")
    private String albumType;

    @SerializedName("href")
    private String url;

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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