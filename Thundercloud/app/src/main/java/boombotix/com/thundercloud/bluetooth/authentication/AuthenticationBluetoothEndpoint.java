package boombotix.com.thundercloud.bluetooth.authentication;

import boombotix.com.thundercloud.model.authentication.AuthenticationStatus;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import boombotix.com.thundercloud.model.MusicService;
import rx.Observable;

/**
 * Endpoint for sending and receiving all authentication-related communication with the speaker
 *
 * @author Theo Kanning
 */
public interface AuthenticationBluetoothEndpoint {

    Observable<OAuthCredentials> updateCredentials(OAuthCredentials credentials,
            MusicService service);

    Observable<OAuthCredentials> refreshService(MusicService service);

    Observable<AuthenticationStatus> getStatus(MusicService service);
}
