package boombotix.com.thundercloud.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.AuthRefreshResponse;

/**
 * Created by jsaucedo on 1/28/16.
 */
public class Auth {

    private static Auth authInstance;
    private static String authToken;
    private static String refreshToken;

    public static Auth getAuthInstance(){
        if(authInstance == null) authInstance = new Auth();
        return authInstance;
    }

    public interface AuthRefreshRespCallback {
        void onSuccess(AuthRefreshResponse authRefreshResponse);
        void onError(VolleyError error);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        Auth.authToken = authToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        Auth.refreshToken = refreshToken;
    }

    public void RefreshAuthToken(Activity activity, String refreshToken, AuthRefreshRespCallback authRefreshRespCallback){
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, BuildConfig.SPOTIFY_TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AuthRefreshResponse resp = new Gson().fromJson(response, AuthRefreshResponse.class);
                String authToken = resp.getAccessToken();

                // put into shared preferences
                SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(activity.getString(R.string.auth_token), authToken);
                editor.commit();

                authRefreshRespCallback.onSuccess(resp);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                authRefreshRespCallback.onError(error);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "refresh_token");
                params.put("refresh_token", refreshToken);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //apparently this breaks everything
//              params.put("Content-Type", "application/x-www-form-urlencoded");
                StringBuilder sb = new StringBuilder();
                sb.append(BuildConfig.SPOTIFY_CLIENT_ID);
                sb.append(":");
                sb.append(BuildConfig.SPOTIFY_CLIENT_SECRET);
                byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
                params.put("Authorization", "Basic " + new String(encoded));
                return params;
            }
        };
        queue.add(sr);
    }
}
