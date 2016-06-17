package boombotix.com.thundercloud.model.authentication;

import com.google.gson.annotations.SerializedName;

import hugo.weaving.DebugLog;

/**
 * Response returned by Spotify when we request a new token using the refresh token
 *
 * Created by jsaucedo on 1/28/16.
 */
public class AuthRefreshResponse {
    @DebugLog
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private int expiresIn;
}
