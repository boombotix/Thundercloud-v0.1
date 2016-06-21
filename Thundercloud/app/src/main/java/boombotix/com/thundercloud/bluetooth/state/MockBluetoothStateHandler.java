package boombotix.com.thundercloud.bluetooth.state;

import android.bluetooth.BluetoothAdapter;

/**
 * Mock bluetooth state handler to be used for mock and test builds
 *
 * Created by kriedema on 6/14/16.
 */
public class MockBluetoothStateHandler implements BluetoothStateHandler {
    @Override
    public int getConnectionState() {
        return BluetoothAdapter.STATE_OFF;
    }
}
