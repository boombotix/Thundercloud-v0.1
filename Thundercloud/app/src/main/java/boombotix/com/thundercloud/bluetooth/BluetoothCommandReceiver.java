package boombotix.com.thundercloud.bluetooth;

import javax.inject.Inject;

/**
 * Listens for bluetooth messages and parses them into command flags and bytes, then decodes the
 * bytes and sends the result to the corresponding listener.
 *
 * @author Theo Kanning
 */
public class BluetoothCommandReceiver {

    private BluetoothMessageWrapper bluetoothMessageWrapper;

    @Inject
    public BluetoothCommandReceiver(
            BluetoothMessageWrapper bluetoothMessageWrapper) {
        this.bluetoothMessageWrapper = bluetoothMessageWrapper;
    }
}
