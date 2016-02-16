package boombotix.com.thundercloud.wifi;

import java.util.List;

import boombotix.com.thundercloud.model.WifiCredentials;
import rx.Observable;

import boombotix.com.thundercloud.model.WifiNetwork;

/**
 * Endpoint for sending and receiving wifi-related communications with the speaker
 *
 * @author Theo Kanning
 */
public interface WifiConnectionEndpoint {
    Observable<List<WifiNetwork>> getAvailableNetworks();
    Observable<Boolean> connectToWifiNetwork(WifiCredentials wifiCredentials);
    Observable<WifiNetwork> getWifiStatus(); //todo add wifi status object?
}
