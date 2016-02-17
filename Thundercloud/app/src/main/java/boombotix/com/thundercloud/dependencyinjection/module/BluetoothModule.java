package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.bluetooth.BluetoothConnection;
import boombotix.com.thundercloud.bluetooth.BluetoothMessageWrapper;
import boombotix.com.thundercloud.bluetooth.authentication.AuthenticationBoombotBluetoothEndpoint;
import boombotix.com.thundercloud.bluetooth.playback.MusicPlaybackBoombotBluetoothEndpoint;
import boombotix.com.thundercloud.bluetooth.wifi.WifiBoombotBluetoothEndpoint;
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
    BluetoothCommandSender provideBluetoothCommandSender() {
        return new BluetoothCommandSender();
    }

    @Singleton
    @Provides
    BluetoothCommandReceiver provideBluetoothCommandReciever() {
        return new BluetoothCommandReceiver();
    }

    @Singleton
    @Provides
    BluetoothMessageWrapper provideBluetoothMessageWrapper() {
        return new BluetoothMessageWrapper();
    }

    @Singleton
    @Provides
    AuthenticationBoombotBluetoothEndpoint provideAuthenticationBoombotBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        return new AuthenticationBoombotBluetoothEndpoint(sender, receiver);
    }

    @Singleton
    @Provides
    WifiBoombotBluetoothEndpoint provideWifiBoombotBluetoothEndpoint() {
        return new WifiBoombotBluetoothEndpoint();
    }

    @Singleton
    @Provides
    MusicPlaybackBoombotBluetoothEndpoint provideMusicPlaybackBoombotBluetoothEndpoint() {
        return new MusicPlaybackBoombotBluetoothEndpoint();
    }

}
