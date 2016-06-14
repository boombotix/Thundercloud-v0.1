package boombotix.com.thundercloud.model.music;

/**
 * Generic artist object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Artist implements MusicListItem{
    public String name;
    public String artworkUrl;
    public Service service;
    public String uri;

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
    public String getSubtitle2() {
        return null;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
