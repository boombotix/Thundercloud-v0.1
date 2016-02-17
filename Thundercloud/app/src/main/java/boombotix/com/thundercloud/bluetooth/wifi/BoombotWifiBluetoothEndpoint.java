package boombotix.com.thundercloud.bluetooth.wifi;

import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.model.wifi.WifiCredentials;
import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import rx.Observable;

/**
 * Implementation of {@link WifiBluetoothEndpoint} that sends wifi commands to the connected Boombot
 *
 * @author Theo Kanning
 */
public class BoombotWifiBluetoothEndpoint implements WifiBluetoothEndpoint{

    private BluetoothCommandSender sender;
    private BluetoothCommandReceiver receiver;

    @Inject
    public BoombotWifiBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public Observable<List<WifiNetwork>> getAvailableNetworks() {
        return null;
    }

    @Override
    public Observable<Boolean> connectToWifiNetwork(WifiCredentials wifiCredentials) {
        return null;
    }

    @Override
    public Observable<WifiNetwork> getWifiStatus() {
        return null;
    }
}
