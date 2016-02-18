package boombotix.com.thundercloud.model.authentication;

/**
 * All information required to send to speaker after user logs in to OAuth system
 *
 * @author Theo Kanning
 */
public class OAuthCredentials {
    private String accessToken;
    private String refreshToken;
    private long expireTime;

    public OAuthCredentials(){

    }

    public OAuthCredentials(String accessToken, String refreshToken, long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpireTime(long expireTime) {
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
