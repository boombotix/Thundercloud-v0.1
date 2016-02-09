package boombotix.com.thundercloud.authentication;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.model.AuthRefreshResponse;
import kaaes.spotify.webapi.android.SpotifyApi;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jsaucedo on 1/28/16.
 */
@Singleton
public class AuthManager {

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
        this.accessToken = accessToken;
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

        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        if(sharedPreferences.contains(application.getString(R.string.user_id))) {
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

    public DateTime getExpires() {
        if(sharedPreferences.contains(application.getString(R.string.expires))) {
            expires = new DateTime(sharedPreferences.getLong(application.getString(R.string.expires), 0));
        }
        return expires;
    }

    public void setExpires(DateTime expires) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(application.getString(R.string.expires), expires.getMillis());
        editor.commit();
        this.expires = expires;
    }

    public boolean isExpired(){
        if(getExpires() == null){
            return true;
        }
        DateTime dateTime = new DateTime(getExpires());
        return dateTime.isBeforeNow();
    }

    /**
     *
     * This function is designed to be flatmapped into any call that requires user authentication
     * It will cehck if the token is expired and renew it.
     *
     * @return returns observable for authtoken request
     */
    public Observable<AuthRefreshResponse> getValidAccessToken(){

        if(isExpired()) {
            Observable observable = spotifyAuthenticationEndpoint.getToken("Basic " + getEncodedAuthHeader(), "refresh_token", getRefreshToken());

            observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).share()
            .subscribe(new Action1() {
                @Override
                public void call(Object o) {
                    Log.e("AuthManager", "Token expired!");
                    handleRefreshResponse((AuthRefreshResponse) o);
                }
            });

            return observable;
        }
        else{
            Log.e("AuthManager", "token not expired!");
            AuthRefreshResponse authRefreshResponse = new AuthRefreshResponse();
            authRefreshResponse.setAccessToken(getAccessToken());
            authRefreshResponse.setExpiresIn(3600);
            return Observable.just(authRefreshResponse);
        }
    }

    public void refreshAuthToken(AuthRefreshRespCallback authRefreshRespCallback){
        spotifyAuthenticationEndpoint.getToken("Basic " + getEncodedAuthHeader(), "refresh_token", getRefreshToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authRefreshResponse -> {
                    handleRefreshResponse(authRefreshResponse);
                    authRefreshRespCallback.onSuccess(authRefreshResponse);
                }, throwable -> {
                    authRefreshRespCallback.onError(throwable);
                });
    }

    private void handleRefreshResponse(AuthRefreshResponse authRefreshResponse) {
        spotifyApi.setAccessToken(authRefreshResponse.getAccessToken());
        setAccessToken(authRefreshResponse.getAccessToken());
        DateTime expires = (new DateTime()).plusSeconds(authRefreshResponse.getExpiresIn());
        setExpires(expires);
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
