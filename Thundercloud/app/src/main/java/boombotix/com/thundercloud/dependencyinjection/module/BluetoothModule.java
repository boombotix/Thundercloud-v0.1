package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.bluetooth.BluetoothConnection;
import boombotix.com.thundercloud.bluetooth.BluetoothMessageWrapper;
import boombotix.com.thundercloud.bluetooth.authentication.BoombotAuthenticationBluetoothEndpoint;
import boombotix.com.thundercloud.bluetooth.authentication.MockAuthenticationEndpoint;
import boombotix.com.thundercloud.bluetooth.playback.BoombotMusicPlaybackBluetoothEndpoint;
import boombotix.com.thundercloud.bluetooth.state.BluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.state.BoombotixBluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.state.MockBluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.wifi.BoombotWifiBluetoothEndpoint;
import dagger.Module;
import dagger.Provides;

@Module
public class BluetoothModule {

    @Provides
    @Singleton
    BluetoothConnection provideBluetoothConnection() {
        return new BluetoothConnection();
    }

    @Singleton
    @Provides
    BluetoothCommandSender provideBluetoothCommandSender(BluetoothMessageWrapper wrapper, BluetoothConnection connection) {
        return new BluetoothCommandSender(wrapper, connection);
    }

    @Singleton
    @Provides
    BluetoothCommandReceiver provideBluetoothCommandReceiver(BluetoothMessageWrapper wrapper, BluetoothConnection connection) {
        return new BluetoothCommandReceiver(wrapper, connection);
    }

    @Singleton
    @Provides
    BluetoothMessageWrapper provideBluetoothMessageWrapper() {
        return new BluetoothMessageWrapper();
    }

    @Singleton
    @Provides
    BoombotAuthenticationBluetoothEndpoint provideAuthenticationBoombotBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        return new BoombotAuthenticationBluetoothEndpoint(sender, receiver);
    }

    @Singleton
    @Provides
    MockAuthenticationEndpoint provideMockAuthenticationEndpoint(
            SpotifyAuthenticationEndpoint spotifyEndpoint,
            AuthManager authManager ) {
        return new MockAuthenticationEndpoint(spotifyEndpoint, authManager);
    }

    @Singleton
    @Provides
    BoombotWifiBluetoothEndpoint provideWifiBoombotBluetoothEndpoint(BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        return new BoombotWifiBluetoothEndpoint(sender, receiver);
    }

    @Singleton
    @Provides
    BoombotMusicPlaybackBluetoothEndpoint provideMusicPlaybackBoombotBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        return new BoombotMusicPlaybackBluetoothEndpoint(sender, receiver);
    }

    @Singleton
    @Provides
    BluetoothStateHandler providesBluetoothStateHandler(){
        if(BuildConfig.DEBUG){
            return new MockBluetoothStateHandler();
        }
        return new BoombotixBluetoothStateHandler();
    }
}
