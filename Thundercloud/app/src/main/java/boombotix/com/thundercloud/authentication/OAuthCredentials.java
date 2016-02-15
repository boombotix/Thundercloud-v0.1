package boombotix.com.thundercloud.authentication;

/**
 * All information required to send to speaker after user logs in to OAuth system
 *
 * @author Theo Kanning
 */
public class OAuthCredentials {
    private String accessToken;
    private String refreshToken;
    private long expireTime;

    public OAuthCredentials(String accessToken, String refreshToken, long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpireTime() {
        return expireTime;
    }
}
