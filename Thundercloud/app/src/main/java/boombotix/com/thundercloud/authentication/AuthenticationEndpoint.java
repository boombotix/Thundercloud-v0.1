package boombotix.com.thundercloud.authentication;

import boombotix.com.thundercloud.model.MusicService;
import rx.Observable;

/**
 * Endpoint for sending and receiving all authentication-related communication with the speaker
 *
 * @author Theo Kanning
 */
public interface AuthenticationEndpoint {

    Observable<OAuthCredentials> updateCredentials(OAuthCredentials credentials,
            MusicService service);

    Observable<OAuthCredentials> refreshService(MusicService service);

    Observable<AuthenticationStatus> getStatus(MusicService service);
}
