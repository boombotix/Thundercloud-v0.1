package boombotix.com.thundercloud.model.music;

/**
 * Generic playlist object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Playlist implements MusicListItem {

    public String name;
    public int totalSongs;
    public String artworkUrl;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return String.valueOf(totalSongs);
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }
}
