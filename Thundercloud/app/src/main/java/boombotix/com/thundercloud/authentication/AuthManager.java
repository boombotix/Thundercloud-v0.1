package boombotix.com.thundercloud.authentication;

import android.app.Application;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.base.RxTransformers;
import boombotix.com.thundercloud.model.authentication.AuthRefreshResponse;
import hugo.weaving.DebugLog;
import kaaes.spotify.webapi.android.SpotifyApi;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by jsaucedo on 1/28/16.
 */
@Singleton
public class AuthManager {

    public static final String GRANT_TYPE = "refresh_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    private String accessToken;

    private String refreshToken;

    private String userId;

    private DateTime expires;

    private Application application;

    private SharedPreferences sharedPreferences;

    private SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint;

    @Inject
    SpotifyApi spotifyApi;

    @Inject
    AuthManager(Application application, SharedPreferences sharedPreferences,
            SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint) {
        this.application = application;
        this.sharedPreferences = sharedPreferences;
        this.spotifyAuthenticationEndpoint = spotifyAuthenticationEndpoint;
    }

    public interface AuthRefreshRespCallback {

        void onSuccess(AuthRefreshResponse authRefreshResponse);

        void onError(Throwable error);
    }

    @DebugLog
    public String getAccessToken() {
        if (sharedPreferences.contains(application.getString(R.string.access_token))) {
            accessToken = sharedPreferences
                    .getString(application.getString(R.string.access_token), null);
        }
        return accessToken;
    }

    @DebugLog
    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.access_token), accessToken);
        editor.commit();
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        if (sharedPreferences.contains(application.getString(R.string.refresh_token))) {
            refreshToken = sharedPreferences
                    .getString(application.getString(R.string.refresh_token), null);
        }
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.refresh_token), refreshToken);
        editor.commit();

        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        if (sharedPreferences.contains(application.getString(R.string.user_id))) {
            userId = sharedPreferences.getString(application.getString(R.string.user_id), null);
        }
        return userId;
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.user_id), userId);
        editor.commit();
        this.userId = userId;
    }

    @DebugLog
    public DateTime getExpires() {
        if (sharedPreferences.contains(application.getString(R.string.expires))) {
            expires = new DateTime(
                    sharedPreferences.getLong(application.getString(R.string.expires), 0));
        }
        return expires;
    }

    public void setExpires(DateTime expires) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(application.getString(R.string.expires), expires.getMillis());
        editor.commit();
        this.expires = expires;
    }

    @DebugLog
    public boolean isExpired() {
        if (getExpires() == null) {
            return true;
        }
        DateTime dateTime = new DateTime(getExpires());
        return dateTime.isBeforeNow();
    }

    /**
     * This function is designed to be flatmapped into any call that requires user authentication It
     * will check if the token is expired and renew it.
     *
     * @return returns observable for authtoken request
     */
    @DebugLog
    public Observable<AuthRefreshResponse> getValidAccessToken() {

        if (isExpired()) {
            Observable<AuthRefreshResponse> observable = spotifyAuthenticationEndpoint
                    .getToken("Basic " + getEncodedAuthHeader(), GRANT_TYPE, getRefreshToken(), getScopes());

            observable.compose(RxTransformers.applySchedulers())
                    .share()
                    .subscribe(
                            response -> {
                                Timber.e("Token expired!");
                                handleRefreshResponse(response);
                            },
                            throwable -> Timber.e(throwable.getMessage()));

            return observable;
        }

        return Observable.just(new AuthRefreshResponse());
    }

    public void refreshAuthToken(AuthRefreshRespCallback authRefreshRespCallback) {
        spotifyAuthenticationEndpoint
                .getToken(getEncodedAuthHeader(), REFRESH_TOKEN, getRefreshToken(), getScopes())
                .compose(RxTransformers.applySchedulers())
                .subscribe(authRefreshResponse -> {
                    handleRefreshResponse(authRefreshResponse);
                    authRefreshRespCallback.onSuccess(authRefreshResponse);
                }, authRefreshRespCallback::onError);
    }

    /**
     * returns the list of all Spotify scopes that are needed for authentication and playback
     *
     * @return
     *  List<String> list of string representations of scopes to be passed to the Spotify endpoint
     */
    private List<String> getScopes(){
        return Arrays.asList(
                "streaming",
                "user-library-read",
                "user-read-private",
                "user-follow-read",
                "playlist-modify-public",
                "user-library-modify",
                "user-follow-modify");
    }

    @DebugLog
    private void handleRefreshResponse(AuthRefreshResponse authRefreshResponse) {
        spotifyApi.setAccessToken(authRefreshResponse.getAccessToken());
        setAccessToken(authRefreshResponse.getAccessToken());
        DateTime expires = new DateTime().plusSeconds(authRefreshResponse.getExpiresIn());
        setExpires(expires);
    }

    private String getEncodedAuthHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("Basic ");
        sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
        sb.append(":");
        sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
        byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
        return new String(encoded);
    }
}
