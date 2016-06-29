package boombotix.com.thundercloud.bluetooth.connection;

import java.io.IOException;
import java.util.UUID;

import boombotix.com.thundercloud.model.constants.BluetoothConstants;
import okio.BufferedSink;
import okio.BufferedSource;

/**
 * Mock BluetoothConnection that returns mock implimentations of BufferedSink and BufferedSource.
 * Useful for testing the flow of the application while the Bluetooth spec has not been solidified.
 *
 * Created by kriedema on 6/27/16.
 */
public class MockBluetoothConnection implements BluetoothClassicConnection {
    @Override
    public BufferedSink outputBuffer(String macAddress) throws IOException {
        return outputBuffer(macAddress, BluetoothConstants.SPP_STANDARD_UUID);
    }

    @Override
    public BufferedSink outputBuffer(String macAddress, UUID serviceUuid) throws IOException {
        return new MockBufferedSink();
    }

    @Override
    public BufferedSource inputBuffer(String macAddress) throws IOException {
        return inputBuffer(macAddress, BluetoothConstants.SPP_STANDARD_UUID);
    }

    @Override
    public BufferedSource inputBuffer(String macAddress, UUID serviceUuid) throws IOException {
        return new MockBufferedSource();
    }
}

