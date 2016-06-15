package boombotix.com.thundercloud.bluetooth.authentication;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.authentication.AuthenticationStatus;
import boombotix.com.thundercloud.model.authentication.MusicService;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import boombotix.com.thundercloud.model.authentication.SpotifyAuthResponseMapper;
import rx.Observable;

/**
 * Mocked authentication endpoint for developing without connected speaker.
 *
 * @author Theo Kanning
 */
@Singleton
public class MockAuthenticationEndpoint implements AuthenticationBluetoothEndpoint {

    private static final int DELAY_MS = 500;

    private SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint;
    //todo replace with class used by speaker

    private AuthManager authManager;

    @Inject
    public MockAuthenticationEndpoint(
            SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint,
            AuthManager authManager) {
        this.spotifyAuthenticationEndpoint = spotifyAuthenticationEndpoint;
        this.authManager = authManager;
    }

    @Override
    public Observable<Boolean> updateCredentials(OAuthCredentials credentials,
            MusicService service) {
        return Observable.just(true)
                .delay(DELAY_MS, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<OAuthCredentials> refreshService(MusicService service) {
        if (service != MusicService.SPOTIFY) {
            return Observable.just(null); //not yet implemented
        }

        String refreshToken = authManager.getRefreshToken();
        SpotifyAuthResponseMapper mapper = new SpotifyAuthResponseMapper();

        Observable<OAuthCredentials> observable = spotifyAuthenticationEndpoint
                .getToken("Basic " + getEncodedAuthHeader(), "refresh_token", refreshToken)
                .map((authRefreshResponse) -> mapper.transform(authRefreshResponse, refreshToken));

        return observable;
    }

    @Override
    public Observable<AuthenticationStatus> getStatus(MusicService service) {
        AuthenticationStatus status = new AuthenticationStatus(service, 0, "");
        return Observable.just(status)
                .delay(DELAY_MS, TimeUnit.MILLISECONDS);
    }

    private String getEncodedAuthHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
        sb.append(":");
        sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
        byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
        return new String(encoded);
    }
}
