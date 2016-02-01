package boombotix.com.thundercloud.authentication;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.model.AuthRefreshResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jsaucedo on 1/28/16.
 */
@Singleton
public class AuthManager {

    private static String authToken;
    private static String refreshToken;
    private static String userId;
    private static Application application;
    private static SharedPreferences sharedPreferences;
    private static SpotifyAuthenticationEndpoint spotifyAuthenticationEndpoint;

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

    public String getAuthToken() {
        if(sharedPreferences.contains(application.getString(R.string.auth_token))) {
            authToken = sharedPreferences.getString(application.getString(R.string.auth_token), null);
        }
        return authToken;
    }

    public void setAuthToken(String authToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.auth_token), authToken);
        editor.commit();
        AuthManager.authToken = authToken;
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
//        if(sharedPreferences.contains(application.getString(R.string.refresh_token))) {
//            userId = sharedPreferences.getString(application.getString(R.string.user_id), null);
//        }
        return userId;
    }

    public static void setUserId(String userId) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(application.getString(R.string.user_id), userId);
//        editor.commit();
        AuthManager.userId = userId;
    }

    public void RefreshAuthToken(AuthRefreshRespCallback authRefreshRespCallback){
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
        sb.append(":");
        sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
        byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
        spotifyAuthenticationEndpoint.getToken("Basic " + new String(encoded), "refresh_token", refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authRefreshResponse -> {
                    authRefreshRespCallback.onSuccess(authRefreshResponse);
                }, throwable -> {
                    authRefreshRespCallback.onError(throwable);
                });
    }
}
