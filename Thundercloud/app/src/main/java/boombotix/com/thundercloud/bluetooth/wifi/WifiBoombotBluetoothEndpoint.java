package boombotix.com.thundercloud.bluetooth.wifi;

import java.util.List;

import boombotix.com.thundercloud.model.WifiCredentials;
import boombotix.com.thundercloud.model.WifiNetwork;
import rx.Observable;

/**
 * Implementation of {@link WifiBluetoothEndpoint} that sends wifi commands to the connected Boombot
 *
 * @author Theo Kanning
 */
public class WifiBoombotBluetoothEndpoint implements WifiBluetoothEndpoint{

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
