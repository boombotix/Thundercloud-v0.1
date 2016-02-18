package boombotix.com.thundercloud.model.music;

/**
 * Generic artist object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Artist implements MusicListItem{
    public String name;
    public String artworkUrl;

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
}
