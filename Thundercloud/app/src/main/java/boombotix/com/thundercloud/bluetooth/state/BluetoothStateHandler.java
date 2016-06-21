package boombotix.com.thundercloud.bluetooth.state;

/**
 * Interface defining how bluetooth state should be handled. This should be injected as a singleton
 *
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
