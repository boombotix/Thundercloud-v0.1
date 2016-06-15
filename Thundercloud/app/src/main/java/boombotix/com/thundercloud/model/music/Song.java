package boombotix.com.thundercloud.model.music;

/**
 * Generic song object to which all api responses are mapped
 *
 * @author Theo Kanning
 */
public class Song implements MusicListItem{

    private String name;
    private String artist; //todo change to list of strings?
    private String artworkUrl;
    private String album;
    private String uri;


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
    public String getUri() {
        return uri;
    }

    @Override
    public String getSubtitle2() {
        return album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
