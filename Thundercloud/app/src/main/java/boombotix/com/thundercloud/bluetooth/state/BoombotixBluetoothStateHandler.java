package boombotix.com.thundercloud.bluetooth.state;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;

/**
 * Created by kriedema on 6/14/16.
 */
public class BoombotixBluetoothStateHandler implements BluetoothStateHandler {
    @Override
    public int getConnectionState() {
        // assuming that the Boombotix speaker registers as a standard playback device
        return BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.A2DP);
    }
}
