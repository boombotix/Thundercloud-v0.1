package boombotix.com.thundercloud.model;

import boombotix.com.thundercloud.R;

/**
 * Enum of all available music services, used for display purposes
 *
 * @author Theo Kanning
 */
public enum MusicService {
    SPOTIFY("Spotify", R.mipmap.ic_launcher),
    SOUND_CLOUD("Sound Cloud", R.mipmap.ic_launcher);

    private String name;

    private int logoId;

    MusicService(String name, int logoId) {
        this.name = name;
        this.logoId = logoId;
    }

    public String getName() {
        return name;
    }

    public int getLogoId() {
        return logoId;
    }
}
