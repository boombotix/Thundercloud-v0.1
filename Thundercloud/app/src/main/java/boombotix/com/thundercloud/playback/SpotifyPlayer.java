package boombotix.com.thundercloud.playback;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Connectivity;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.music.MusicListItem;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by kriedema on 6/14/16.
 */
public class SpotifyPlayer implements MusicPlayer, Player.InitializationObserver,
        ConnectionStateCallback, PlayerNotificationCallback{

    AuthManager authManager;
    Context context;

    Player player;
    MusicListItem item;

    public SpotifyPlayer(Context context, AuthManager authManager){
        this.context = context;
        this.authManager = authManager;
        initializePlayer();
    }

    @DebugLog
    @Override
    public void play(MusicListItem item) {
        if(player != null){
            this.item = item;
            Timber.d(item.getUri());
            player.play(this.item.getUri());
        }
    }

    @DebugLog
    @Override
    public void pause() {

    }

    @DebugLog
    @Override
    public void stop() {
        item = null;
    }

    @DebugLog
    @Override
    public void dispose() {
        player.removeConnectionStateCallback(this);
        player.removePlayerNotificationCallback(this);

        Spotify.destroyPlayer(player);
    }

    @DebugLog
    private void initializePlayer() {
        initializePlayerWithToken(authManager.getAccessToken());
    }

    public void initializePlayerWithToken(String validOauthToken){
        Config playerConfig = new Config(context, validOauthToken, BuildConfig.SPOTIFY_CLIENT_ID);
        Spotify.getPlayer(playerConfig, context, this);
    }

    @DebugLog
    @Override
    public void onInitialized(Player player) {
        this.player = player;

        this.player.login(authManager.getAccessToken());

        player.setConnectivityStatus(getNetworkConnectivity(context));
        player.addPlayerNotificationCallback(this);
        player.addConnectionStateCallback(this);
    }

    @DebugLog
    private Connectivity getNetworkConnectivity(Context context) {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return Connectivity.fromNetworkType(activeNetwork.getType());
        } else {
            return Connectivity.OFFLINE;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Timber.e(throwable.getMessage());
    }

    @Override
    public void onLoggedIn() {
        Timber.d("Spotify user logged in");
    }

    @Override
    public void onLoggedOut() {
        Timber.d("Spotify user logged out");
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        Timber.d(throwable, "Spotify login failed");
    }

    @Override
    public void onTemporaryError() {
        Timber.e("Spotify player temporary error");
    }

    @Override
    public void onConnectionMessage(String s) {
        Timber.d(s);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Timber.d("Spotify PlaybackEvent: " + eventType.toString());
        Timber.d("Spotify is playing: " + playerState.playing);
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {
        Timber.e(errorType.name());
        Timber.d(s);
    }
}
