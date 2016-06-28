package boombotix.com.thundercloud.bluetooth.connection;

import java.io.IOException;
import java.util.UUID;

import okio.BufferedSink;
import okio.BufferedSource;

/**
 * Wrapper around out Bluetooth connection that returns okio buffers.
 *
 * Created by kriedema on 6/24/16.
 */
public interface BluetoothClassicConnection {
    BufferedSink outputBuffer(String macAddress) throws IOException;
    BufferedSink outputBuffer(String macAddress, UUID serviceUuid) throws IOException;

    BufferedSource inputBuffer(String macAddress) throws IOException;
    BufferedSource inputBuffer(String macAddress, UUID serviceUuid) throws IOException;
}
