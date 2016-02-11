package boombotix.com.thundercloud.model.music;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
public class Album implements MusicListItem{
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
