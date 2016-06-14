package boombotix.com.thundercloud.bluetooth.state;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by kriedema on 6/14/16.
 */
public class MockBluetoothStateHandler implements BluetoothStateHandler {
    @Override
    public int getConnectionState() {
        return BluetoothAdapter.STATE_OFF;
    }
}
