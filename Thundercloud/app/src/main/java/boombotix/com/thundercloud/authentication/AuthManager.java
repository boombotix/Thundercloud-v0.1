package boombotix.com.thundercloud.authentication;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.model.AuthRefreshResponse;
import kaaes.spotify.webapi.android.SpotifyApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsaucedo on 1/28/16.
 */
@Singleton
public class AuthManager {

    private static String accessToken;
    private static String refreshToken;
    private static String userId;
    private static Date expires;
    private static Application application;
    private static SharedPreferences sharedPreferences;
    private static SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint;
    @Inject
    SpotifyApi spotifyApi;

    @Inject
    AuthManager(Application application, SharedPreferences sharedPreferences, SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint){
        this.application = application;
        this.sharedPreferences = sharedPreferences;
        this.spotifyAuthenticationEndpoint = spotifyAuthenticationEndpoint;
    }

    public interface AuthRefreshRespCallback {
        void onSuccess(AuthRefreshResponse authRefreshResponse);
        void onError(Throwable error);
    }

    public String getAccessToken() {
        if(sharedPreferences.contains(application.getString(R.string.access_token))) {
            accessToken = sharedPreferences.getString(application.getString(R.string.access_token), null);
        }
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.access_token), accessToken);
        editor.commit();
        AuthManager.accessToken = accessToken;
    }


    public String getRefreshToken() {
        if(sharedPreferences.contains(application.getString(R.string.refresh_token))) {
            refreshToken = sharedPreferences.getString(application.getString(R.string.refresh_token), null);
        }
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.refresh_token), refreshToken);
        editor.commit();

        AuthManager.refreshToken = refreshToken;
    }

    public static String getUserId() {
        if(sharedPreferences.contains(application.getString(R.string.user_id))) {
            userId = sharedPreferences.getString(application.getString(R.string.user_id), null);
        }
        return userId;
    }

    public static void setUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.user_id), userId);
        editor.commit();
        AuthManager.userId = userId;
    }

    public static Date getExpires() {
        if(sharedPreferences.contains(application.getString(R.string.expires))) {
            expires = new Date(sharedPreferences.getLong(application.getString(R.string.expires), 0));
        }
        return expires;
    }

    public static void setExpires(Date expires) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(application.getString(R.string.expires), expires.getTime());
        editor.commit();
        AuthManager.expires = expires;
    }

    public boolean isExpired(){
        if(getExpires() == null) return true;
        return ((new Date()).getTime()/1000 - expires.getTime() >= 3600);
    }

    public void refreshAuthToken(AuthRefreshRespCallback authRefreshRespCallback){
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
        sb.append(":");
        sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
        byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
        spotifyAuthenticationEndpoint.getToken("Basic " + new String(encoded), "refresh_token", getRefreshToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authRefreshResponse -> {
                    spotifyApi.setAccessToken(authRefreshResponse.getAccessToken());
                    setAccessToken(authRefreshResponse.getAccessToken());
                    setExpires(new Date((new Date()).getTime()/1000 + authRefreshResponse.getExpiresIn()));
                    authRefreshRespCallback.onSuccess(authRefreshResponse);
                }, throwable -> {
                    authRefreshRespCallback.onError(throwable);
                });
    }
}
