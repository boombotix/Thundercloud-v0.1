package boombotix.com.thundercloud.model.authentication;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.music.Service;

/**
 * Enum of all available music services, used for display purposes
 *
 * @author Theo Kanning
 */
public enum MusicService {
    SPOTIFY(Service.Spotify, R.mipmap.ic_launcher),
    SLACKER(Service.Slacker, R.mipmap.ic_launcher);

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
