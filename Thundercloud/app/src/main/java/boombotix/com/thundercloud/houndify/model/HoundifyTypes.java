package boombotix.com.thundercloud.houndify.model;

/**
 * Created by kriedema on 6/10/16.
 */
public class HoundifyTypes {
    public enum ValidCommandTypes {
        MusicCommand,
        MusicPlayerCommand,
        MusicThirdPartyLauncherCommand
    }

    public enum MusicCommandTypes {
        MusicSearchCommand,
        MusicChartsCommand
    }

    public enum ThirdPartyMusicServices {
        Spotify,
        Pandora,
        AppleMusic
    }
}
