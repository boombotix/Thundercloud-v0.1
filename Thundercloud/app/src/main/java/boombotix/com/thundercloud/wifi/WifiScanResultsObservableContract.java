package boombotix.com.thundercloud.wifi;

import java.util.List;

import javax.inject.Singleton;

import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import rx.Observable;
import rx.subjects.Subject;

/**
 * Created by kriedema on 6/23/16.
 */
@Singleton
public interface WifiScanResultsObservableContract {
    Observable<List<WifiNetwork>> getScanResults();

    Subject<List<WifiNetwork>, List<WifiNetwork>> getScanResultsSubject();
}
