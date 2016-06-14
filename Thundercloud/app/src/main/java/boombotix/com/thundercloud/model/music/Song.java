package boombotix.com.thundercloud.model.music;

/**
 * Generic song object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Song implements MusicListItem{
    public String name;
    public String artist; //todo change to list of strings?
    public String artworkUrl;
    public String album;
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
        return album;
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
