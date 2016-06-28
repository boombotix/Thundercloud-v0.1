package boombotix.com.thundercloud.dependencyinjection.module;

import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.connection.MockBluetoothConnection;

/**
 * Created by kriedema on 6/28/16.
 */
public class OverrideBluetoothModule extends BluetoothModule {
    @Override
    BluetoothClassicConnection providesBluetoothClassicConnection() {
        return new MockBluetoothConnection();
    }
}
