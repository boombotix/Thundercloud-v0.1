package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.connection.MockBluetoothConnection;
import boombotix.com.thundercloud.bluetooth.state.BluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.state.MockBluetoothStateHandler;
import boombotix.com.thundercloud.bluetooth.wifi.CredentialsFormatter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kriedema on 6/27/16.
 */
@Module
public class MockBluetoothModule {
    @Singleton
    @Provides
    BluetoothStateHandler providesBluetoothStateHandler(){
        return new MockBluetoothStateHandler();
    }

    @Singleton
    @Provides
    BluetoothClassicConnection providesBluetoothClassicConnection(){
        return new MockBluetoothConnection();
    }

    @Singleton
    @Provides
    CredentialsFormatter providesCredentialsFormatter(){
        return new CredentialsFormatter();
    }
}
