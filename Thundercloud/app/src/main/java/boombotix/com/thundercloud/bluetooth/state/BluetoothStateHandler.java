package boombotix.com.thundercloud.bluetooth.state;

/**
 * Created by kriedema on 6/14/16.
 */
public interface BluetoothStateHandler {

    /**
     *
     * @return
     *  The constant from BluetoothAdapter representing the connection state
     */
    int getConnectionState();
}
