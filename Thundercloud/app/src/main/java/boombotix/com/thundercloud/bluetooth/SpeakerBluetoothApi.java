package boombotix.com.thundercloud.bluetooth;

import boombotix.com.thundercloud.model.MusicService;
import boombotix.com.thundercloud.model.WifiCredentials;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import boombotix.com.thundercloud.model.playback.MusicPlaybackAction;

/**
 * All commands that can be sent to the speaker over bluetooth. All returns types are void because
 * each methods requires bluetooth communication to be resolved.
 *
 * @author Theo Kanning
 */
public interface SpeakerBluetoothApi {

    void getSpeakerInfo();

    void getAvailableNetworks();

    void connectToWifiNetwork(WifiCredentials credentials);

    void getWifiStatus();

    void updateCredentials(OAuthCredentials credentials, MusicService service);

    void refreshService(MusicService service);

    void getMusicServiceStatus(MusicService service);

    void controlPlayback(MusicPlaybackAction action);

    void getPlaybackState(); //todo add response type

    void setVolume(int volume); //todo volume type?

    void updateQueue(); //todo queue update request
}
