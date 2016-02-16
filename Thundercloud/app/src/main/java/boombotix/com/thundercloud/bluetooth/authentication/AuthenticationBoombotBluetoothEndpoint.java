package boombotix.com.thundercloud.bluetooth.authentication;

import boombotix.com.thundercloud.model.MusicService;
import boombotix.com.thundercloud.model.authentication.AuthenticationStatus;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import rx.Observable;

/**
 * Implementation of {@link AuthenticationBluetoothEndpoint} that sends authentication info to the
 * Boombot over bluetooth
 *
 * @author Theo Kanning
 */
public class AuthenticationBoombotBluetoothEndpoint implements AuthenticationBluetoothEndpoint{

    @Override
    public Observable<OAuthCredentials> updateCredentials(OAuthCredentials credentials,
            MusicService service) {
        return null;
    }

    @Override
    public Observable<OAuthCredentials> refreshService(MusicService service) {
        return null;
    }

    @Override
    public Observable<AuthenticationStatus> getStatus(MusicService service) {
        return null;
    }
}
