package boombotix.com.thundercloud.model.music;

/**
 * Generic album object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Album implements MusicListItem{
    public String name;
    public String artist;
    public String artworkUrl;
    public Service service;
    public String uri;

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

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
