package boombotix.com.thundercloud.model.music;

/**
 * Created by jsaucedo on 2/17/16.
 */
public class MusicDetailListItem {
    public static int TYPE_ALBUM = 1;
    public static int TYPE_SONG = 2;
    public static int TYPE_PLAYLIST = 3;
    public static int TYPE_ARTIST = 4;
    public boolean isHeader;
    public boolean isLink;
    public int type;
    public String itemText;
    public MusicListItem musicListItem;


    public void setupNewHeader(String headerText){
        itemText = headerText;
        isHeader = true;
    }

    public void setupNewLink(String linkText){
        itemText = linkText;
        isLink = true;
    }
}
