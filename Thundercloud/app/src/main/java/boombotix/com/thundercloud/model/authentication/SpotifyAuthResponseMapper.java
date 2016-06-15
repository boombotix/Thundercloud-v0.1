package boombotix.com.thundercloud.model.authentication;

import org.joda.time.DateTime;

/**
 * Maps a Spotify refresh response, {@link AuthRefreshResponse} to the generic {@link
 * OAuthCredentials}
 *
 * @author Theo Kanning
 */
public class SpotifyAuthResponseMapper {

    public OAuthCredentials transform(AuthRefreshResponse response, String refreshToken) {
        OAuthCredentials credentials = new OAuthCredentials();
        DateTime expireTime = DateTime.now().plusMillis(response.getExpiresIn());

        credentials.setAccessToken(response.getAccessToken());
        credentials.setExpireTime(expireTime.getMillis());
        credentials.setRefreshToken(refreshToken);

        return credentials;
    }
}
