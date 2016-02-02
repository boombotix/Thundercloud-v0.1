package boombotix.com.thundercloud.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.gson.Gson;

import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;
import java.io.IOException;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.AuthRefreshResponse;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements AuthManager.AuthRefreshRespCallback {
    private final String TOKEN_URL = BuildConfig.SPOTIFY_TOKEN_URL;
    private final String AUTH_URL = BuildConfig.SPOTIFY_AUTH_URL;
    private final String CLIENT_ID = BuildConfig.SPOTIFY_CLIENT_ID;
    private final String CLIENT_SECRET = BuildConfig.SPOTIFY_CLIENT_SECRET;

    @Inject
    SpotifyApi spotifyApi;
    @Inject
    Gson gson;
    @Inject
    AuthManager authManager;
    @Bind(R.id.btn_yourmusic)
    Button urmsc;
    @Inject
    SpotifyService spotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        authorizeUser();
        urmsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MusicPagerActivity.class));
            }
        });
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
        // TODO refactor access list into resource file
        builder.setScopes(Arrays.asList("user-read-private", "user-library-read", "user-follow-read"));
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
                    authManager.setAccessToken(credential.getAccessToken());
                    authManager.setRefreshToken(credential.getRefreshToken());
                    authManager.setExpires(new Date((new Date()).getTime() / 1000 + credential.getExpiresInSeconds()));
                    spotifyApi.setAccessToken(credential.getAccessToken());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getUser();
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

    private void useRefreshToken() {
        authManager.refreshAuthToken(this);
    }

    private void getUser() {
        Observable.defer(() -> Observable.just(spotify.getMe()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPrivate -> {
                    AuthManager.setUserId(userPrivate.id);
                    getPlaylistFromResponse(userPrivate);
                });
    }

    private void getPlaylistFromResponse(UserPrivate userPrivate) {
        Observable.defer(() -> Observable.just(spotify.getPlaylists(userPrivate.id)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playlistSimplePager -> {
                    String contents = "";
                    for (PlaylistSimple playlistSimple : playlistSimplePager.items) {
                        contents += playlistSimple.name + "\n";
                    }
                    ((TextView) findViewById(R.id.playlist)).setText(contents);
                });
    }

    @Override
    public void onSuccess(AuthRefreshResponse authRefreshResponse) {
        ((TextView) findViewById(R.id.refresh_resp)).setText(authRefreshResponse.getAccessToken());
        getUser();
    }

    @Override
    public void onError(Throwable error) {
        ((TextView) findViewById(R.id.refresh_resp)).setText(error.getMessage());
    }
}
