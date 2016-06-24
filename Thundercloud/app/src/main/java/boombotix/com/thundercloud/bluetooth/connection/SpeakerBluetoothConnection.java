package boombotix.com.thundercloud.bluetooth.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

import boombotix.com.thundercloud.model.constants.BluetoothConstants;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by kriedema on 6/24/16.
 */
public class SpeakerBluetoothConnection implements BluetoothClassicConnection {
    @Override
    public BufferedSink outputBuffer(String macAddress) throws IOException {
        return outputBuffer(macAddress, BluetoothConstants.SPP_STANDARD_UUID);
    }

    @Override
    public BufferedSink outputBuffer(String macAddress, UUID serviceUuid) throws IOException {
        // system can start discovery at any time, it's good practice to cancel before using bluetooth
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        BluetoothDevice selectedDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress);

        BluetoothSocket socket = selectedDevice.createRfcommSocketToServiceRecord(BluetoothConstants.SPP_STANDARD_UUID);
        socket.connect();
        return Okio.buffer(Okio.sink(socket.getOutputStream()));
    }

    @Override
    public BufferedSource inputBuffer(String macAddress) throws IOException {
        return inputBuffer(macAddress, BluetoothConstants.SPP_STANDARD_UUID);
    }

    @Override
    public BufferedSource inputBuffer(String macAddress, UUID serviceUuid) throws IOException {
        // system can start discovery at any time, it's good practice to cancel before using bluetooth
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        BluetoothDevice selectedDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress);

        BluetoothSocket socket = selectedDevice.createRfcommSocketToServiceRecord(BluetoothConstants.SPP_STANDARD_UUID);
        socket.connect();
        return Okio.buffer(Okio.source(socket.getInputStream()));
    }
}
