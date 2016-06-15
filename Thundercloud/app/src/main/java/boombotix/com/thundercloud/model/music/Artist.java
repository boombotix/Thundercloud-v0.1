package boombotix.com.thundercloud.model.music;

/**
 * Generic artist object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Artist implements MusicListItem{
    private String name;
    private String artworkUrl;
    private String uri;
    private Service service;

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

    @Override
    public Service getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
