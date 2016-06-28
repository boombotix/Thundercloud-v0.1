package boombotix.com.thundercloud.dependencyinjection;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.api.SpotifyTrackEndpoint;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.wifi.CredentialsFormatter;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;
import boombotix.com.thundercloud.houndify.response.HoundifyDeserializer;
import boombotix.com.thundercloud.houndify.response.HoundifyModelExtractor;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.playback.MusicControls;
import boombotix.com.thundercloud.playback.PlaybackQueue;
import boombotix.com.thundercloud.playback.SpotifyPlayer;
import boombotix.com.thundercloud.wifi.WifiScanResultsBroadcastReciever;
import boombotix.com.thundercloud.wifi.WifiScanResultsObservableContract;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by kriedema on 6/27/16.
 */
public interface ThundercloudGraph {

    void inject(WifiScanResultsBroadcastReciever reciever);

    Gson gson();

    SpotifyApi spotifyApi();

    SharedPreferences sharedPreferences();

    AuthManager authManager();

    SpotifyService spotifyService();

    HoundifyResponseParser houndifyHelper();

    HoundifyModelExtractor houndifyModelExtractor();

    HoundifyDeserializer houndifyDeserializer();

    HoundifyRequestTransformer houndifyRequestAdapter();

    PlaybackQueue playbackQueue();

    MusicControls musicControls();

    SpotifyPlayer spotifyPlayer();

    SpotifyTrackEndpoint trackEndpoint();

    SpotifyAuthenticationEndpoint authenticationEndpoint();

    WifiScanResultsObservableContract wifiScanResultObservable();

    BluetoothClassicConnection bluetoothClassicConnection();

    CredentialsFormatter credentialsFormatter();
}
