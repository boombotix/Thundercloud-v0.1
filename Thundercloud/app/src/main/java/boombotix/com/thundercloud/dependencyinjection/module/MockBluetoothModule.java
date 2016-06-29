package boombotix.com.thundercloud.dependencyinjection.module;

import boombotix.com.thundercloud.bluetooth.connection.BluetoothClassicConnection;
import boombotix.com.thundercloud.bluetooth.connection.MockBluetoothConnection;

/**
 * Overrides the BluetoothClassicConection provider from the BluetoothModule.
 *
 * The MockBluetoothConnection returns random results in the absense of an actual connection,
 * so we can continue UI development while the bluetooth spec is being solidified
 */
public class MockBluetoothModule extends BluetoothModule {
    @Override
    BluetoothClassicConnection providesBluetoothClassicConnection() {
        return new MockBluetoothConnection();
    }
}
