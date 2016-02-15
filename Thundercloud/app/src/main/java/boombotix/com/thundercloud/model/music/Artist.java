package boombotix.com.thundercloud.model.music;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
public class Artist implements MusicListItem{
    public String name;
    public String genre; //todo change to list?
    public String artworkUrl;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return genre;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }
}
