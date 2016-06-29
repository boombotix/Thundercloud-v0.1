package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.connection.SpeakerBluetoothConnection;
import boombotix.com.thundercloud.bluetooth.state.BluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.state.BoombotixBluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.wifi.CredentialsFormatter;
import dagger.Module;
import dagger.Provides;

@Module
public class BluetoothModule {
    @Singleton
    @Provides
    BluetoothStateHandler providesBluetoothStateHandler(){
        return new BoombotixBluetoothStateHandler();
    }

    @Singleton
    @Provides
    BluetoothClassicConnection providesBluetoothClassicConnection(){
        return new SpeakerBluetoothConnection();
    }

    @Singleton
    @Provides
    CredentialsFormatter providesCredentialsFormatter(){
        return new CredentialsFormatter();
    }
}
