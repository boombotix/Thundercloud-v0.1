package boombotix.com.thundercloud.model.authentication;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.music.Service;

/**
 * Enum of all available music services, used for display purposes
 *
 * @author Theo Kanning
 */
public enum MusicService {
    SPOTIFY(Service.Spotify, R.drawable.ic_spotify),
    SLACKER(Service.Slacker, R.drawable.ic_play_circle_dark);

    private Service service;

    private int logoId;

    MusicService(Service service, int logoId) {
        this.service = service;
        this.logoId = logoId;
    }

    public Service getService() {
        return service;
    }

    public String getName(){
        return service.toString();
    }

    public int getLogoId() {
        return logoId;
    }
}
