package boombotix.com.thundercloud.model.music;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
public class Song implements MusicListItem{
    public String name;
    public String artist; //todo change to list of strings?
    public String artworkUrl;

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
}
