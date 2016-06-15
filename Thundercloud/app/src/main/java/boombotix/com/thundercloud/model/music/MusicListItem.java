package boombotix.com.thundercloud.model.music;

/**
 * All methods necessary to show an item in a list
 *
 * @author Theo Kanning
 */
public interface MusicListItem {
    String getTitle();
    String getSubtitle();
    String getArtworkUrl();
    String getUri();

    String getSubtitle2();
}
