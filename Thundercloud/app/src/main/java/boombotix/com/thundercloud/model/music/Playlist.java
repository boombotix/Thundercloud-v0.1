package boombotix.com.thundercloud.model.music;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
public class Playlist implements MusicListItem {

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
