package boombotix.com.thundercloud.model.music;

/**
 * Generic album object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Album implements MusicListItem{
    private String name;
    private String artist;
    private String artworkUrl;
    private String uri;
    private Service service;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return artist;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }

    @Override
    public String getSubtitle2() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    @Override
    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
