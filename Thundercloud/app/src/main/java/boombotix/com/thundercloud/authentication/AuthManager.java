package boombotix.com.thundercloud.authentication;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.VolleyError;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
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

    private static AuthManager authInstance;
    private static String authToken;
    private static String refreshToken;
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
        return authToken;
    }

    public void setAuthToken(String authToken) {
        AuthManager.authToken = authToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        AuthManager.refreshToken = refreshToken;
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
//        RequestQueue queue = Volley.newRequestQueue(application);
//        StringRequest sr = new StringRequest(Request.Method.POST, BuildConfig.SPOTIFY_TOKEN_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                AuthRefreshResponse resp = new Gson().fromJson(response, AuthRefreshResponse.class);
//                authToken = resp.getAccessToken();
//
//                // put into shared preferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(application.getString(R.string.auth_token), authToken);
//                editor.commit();
//
//                authRefreshRespCallback.onSuccess(resp);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                authRefreshRespCallback.onError(error);
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("grant_type", "refresh_token");
//                params.put("refresh_token", refreshToken);
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                //apparently this breaks everything
////              params.put("Content-Type", "application/x-www-form-urlencoded");
//                StringBuilder sb = new StringBuilder();
//                sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
//                sb.append(":");
//                sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
//                byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
//                params.put("Authorization", "Basic " + new String(encoded));
//                return params;
//            }
//        };
//        queue.add(sr);
    }
}
