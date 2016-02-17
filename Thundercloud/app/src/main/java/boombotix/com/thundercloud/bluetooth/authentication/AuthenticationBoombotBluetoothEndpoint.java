package boombotix.com.thundercloud.bluetooth.authentication;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.model.authentication.AuthenticationStatus;
import boombotix.com.thundercloud.model.authentication.MusicService;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import rx.Observable;

/**
 * Implementation of {@link AuthenticationBluetoothEndpoint} that sends authentication info to the
 * Boombot over bluetooth
 *
 * @author Theo Kanning
 */
@Singleton
public class AuthenticationBoombotBluetoothEndpoint implements AuthenticationBluetoothEndpoint {

    private BluetoothCommandSender sender;

    private BluetoothCommandReceiver receiver;

    @Inject
    public AuthenticationBoombotBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

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
