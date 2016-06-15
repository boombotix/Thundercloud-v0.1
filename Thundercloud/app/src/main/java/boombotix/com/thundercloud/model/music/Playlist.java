package boombotix.com.thundercloud.model.music;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
public class Playlist implements MusicListItem {

    private String name;
    private String artworkUrl;
    private String uri;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return null;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getSubtitle2() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
