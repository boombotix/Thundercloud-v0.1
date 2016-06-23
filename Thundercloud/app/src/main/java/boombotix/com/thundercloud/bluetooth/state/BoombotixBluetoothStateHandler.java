package boombotix.com.thundercloud.bluetooth.state;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;

/**
 * Concrete implementation designed to handle the bluetooth connectivity to a Boombotix speaker
 *
 * Created by kriedema on 6/14/16.
 */
public class BoombotixBluetoothStateHandler implements BluetoothStateHandler {
    @Override
    public int getConnectionState() {
        // assuming that the Boombotix speaker registers as a standard playback device
        return BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.A2DP);
    }
}
