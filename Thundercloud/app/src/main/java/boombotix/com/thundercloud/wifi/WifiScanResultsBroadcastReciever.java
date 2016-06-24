package boombotix.com.thundercloud.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.dependencyinjection.component.ApplicationComponent;
import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import hugo.weaving.DebugLog;
import rx.Observable;

/**
 * Broadcast reciever registered in the application manifest.
 * Handles intents that signal the system has finished scanning for wireless networks.
 *
 * Created by kriedema on 6/22/16.
 */
public class WifiScanResultsBroadcastReciever extends BroadcastReceiver {
    @Inject
    WifiScanResultsObservableContract wifiScanResultsObservable;

    /**
     * Called by the system when one of the intents defined in the application manifest is fired.
     *
     * @param context
     *      The context on which the service is running
     * @param intent
     *      The intent being sent by the system. Limited by defining filters in the application manifest.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationComponent component = ThundercloudApplication.instance().getApplicationComponent();
        component.inject(this);

        WifiManager wifiManager = (WifiManager) ThundercloudApplication.instance().getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> results = wifiManager.getScanResults();

        // we have to check this way because the extra that will tell you if the operation succeed was added in API 23
        if (results.size() == 0) return;

        sentResultsToSubscribers(results);
    }

    /**
     *
     * Creates an observable from the results
     *
     * Converts the ScanResult objects to WifiNetwork objects
     *
     * Gets rid of duplicates by putting all of the WifiNetworks into a HashSet
     *
     * Passes an ArrayList created from the HashSet to the onNext method of the local subject.
     *
     * @param results
     *      The results we use to get the information sent to the subscriber
     */
    private void sentResultsToSubscribers(List<ScanResult> results){
        Observable.from(results)
                .map(this::mapScanResult)
                .collect(HashSet<WifiNetwork>::new, HashSet<WifiNetwork>::add)
                .map(ArrayList<WifiNetwork>::new)
                .subscribe(list -> wifiScanResultsObservable.getScanResultsSubject().onNext(list))
                .unsubscribe();
    }

    /**
     * Creates a new WifiNetwork object from a ScanResult returned by the system
     *
     * @param scanResult
     *      The ScanResult to be translated to a WifiNetwork
     * @return
     *      The WifiNetwork object containing a subset of the data from the ScanResult
     */
    @DebugLog
    private WifiNetwork mapScanResult(ScanResult scanResult) {
        if(scanResult.SSID.equals("")) scanResult.SSID = "[Unknown Network]";

        return new WifiNetwork(scanResult.SSID, scanResult.level, null);
    }
}
