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

    Service getService();

    String getSubtitle2();

    String getUri();
}
