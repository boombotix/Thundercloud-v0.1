package boombotix.com.thundercloud.dependencyinjection;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import boombotix.com.thundercloud.ApplicationInitializer;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.api.SpotifySearchEndpoint;
import boombotix.com.thundercloud.api.SpotifyTrackEndpoint;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.wifi.CredentialsFormatter;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;
import boombotix.com.thundercloud.houndify.response.HoundifyDeserializer;
import boombotix.com.thundercloud.houndify.response.HoundifyModelExtractor;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.playback.MusicPlayer;
import boombotix.com.thundercloud.playback.SpotifyEngine;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsPlaylistViewHolder;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsTrackViewHolder;
import boombotix.com.thundercloud.wifi.WifiScanResultsBroadcastReciever;
import boombotix.com.thundercloud.wifi.WifiScanResultsObservableContract;

/**
 * General contract for all of our application level dagger components to implement.
 * This lets us have a different application component per build variant
 */
public interface ThundercloudGraph {

    void inject(ThundercloudApplication thundercloudApplication);

    void inject(WifiScanResultsBroadcastReciever reciever);

    void inject(SearchResultsTrackViewHolder searchResultsTrackViewHolder);

    void inject(SearchResultsPlaylistViewHolder searchResultsPlaylistViewHolder);

    ApplicationInitializer applicationInitializer();

    Gson gson();

    SharedPreferences sharedPreferences();

    AuthManager authManager();

    HoundifyResponseParser houndifyHelper();

    HoundifyModelExtractor houndifyModelExtractor();

    HoundifyDeserializer houndifyDeserializer();

    HoundifyRequestTransformer houndifyRequestAdapter();

    MusicPlayer musicControls();

    SpotifyEngine spotifyPlayer();

    SpotifyTrackEndpoint trackEndpoint();

    SpotifySearchEndpoint searchEndpoint();

    SpotifyAuthenticationEndpoint authenticationEndpoint();

    WifiScanResultsObservableContract wifiScanResultObservable();

    BluetoothClassicConnection bluetoothClassicConnection();

    CredentialsFormatter credentialsFormatter();
}
