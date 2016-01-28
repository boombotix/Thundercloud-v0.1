package boombotix.com.thundercloud.ui.activity;

import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.gson.Gson;

import com.spotify.sdk.android.player.Player;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;

public class LoginActivity extends BaseActivity {
    private final String TOKEN_URL = BuildConfig.SPOTIFY_TOKEN_URL;
    private final String AUTH_URL = BuildConfig.SPOTIFY_AUTH_URL;
    private final String CLIENT_ID = BuildConfig.SPOTIFY_CLIENT_ID;
    private final String CLIENT_SECRET = BuildConfig.SPOTIFY_CLIENT_SECRET;

    @Inject
    SpotifyApi api;
    @Inject
    Gson gson;
    private SpotifyService spotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);

        spotify = api.getService();



        authorizeUser();

    }

    private void authorizeUser() {
        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(TOKEN_URL),
                new ClientParametersAuthentication(CLIENT_ID, CLIENT_SECRET),
                CLIENT_ID,
                AUTH_URL);
        builder.setScopes(Arrays.asList("user-read-private"));
        AuthorizationFlow flow = builder.build();

        AuthorizationUIController controller =
                new DialogFragmentController(getFragmentManager()) {

                    @Override
                    public String getRedirectUri() throws IOException {
                        return "http://localhost/Callback";
                    }

                    @Override
                    public boolean isJavascriptEnabledForWebView() {
                        return true;
                    }

                };

        final OAuthManager oauth = new OAuthManager(flow, controller);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    final Credential credential = oauth.authorizeExplicitly("userId", null, null).getResult();
                    useRefreshToken(credential.getRefreshToken());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.auth)).setText(credential.getAccessToken());
                            ((TextView) findViewById(R.id.refresh)).setText(credential.getRefreshToken());
                            ((TextView) findViewById(R.id.expir)).setText(String.valueOf(credential.getExpiresInSeconds()));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

    /*
    * Tried using OkHTTP here but apparently the content-type is overwritten when you make a post
    * request to something that spotify doesn't support.
    */
    private void useRefreshToken(String refreshToken) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TextView) findViewById(R.id.refresh_resp)).setText(response);
                // TODO replace with actual model
                Map<String, String> resp = gson.fromJson(response, Map.class);

                api.setAccessToken(resp.get("access_token"));
                getUser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView) findViewById(R.id.refresh_resp)).setText(error.getMessage());
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
                sb.append(CLIENT_ID);
                sb.append(":");
                sb.append(CLIENT_SECRET);
                byte[] encoded = com.google.api.client.util.Base64.encodeBase64((sb.toString()).getBytes());
                Log.v("encoded", new String(encoded));
                params.put("Authorization", "Basic " + new String(encoded));
                return params;
            }
        };
        queue.add(sr);
    }

    private void getUser() {


        spotify.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, retrofit.client.Response response) {
                getPlaylist(userPrivate.id);
                Log.e("user", userPrivate.display_name+"");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("err", error.getMessage());
                error.printStackTrace();
            }
        });
    }

    private void getPlaylist(String id) {
        spotify.getPlaylists(id, new Callback<Pager<PlaylistSimple>>() {
            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, retrofit.client.Response response) {
                String contents = "";
                for(PlaylistSimple playlistSimple : playlistSimplePager.items){
                    contents += playlistSimple.name +"\n";
                }
                ((TextView) findViewById(R.id.playlist)).setText(contents);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("err", error.getMessage());
                error.printStackTrace();
            }
        });
    }
}
