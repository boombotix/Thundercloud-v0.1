package boombotix.com.thundercloud.wifi;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import javax.inject.Singleton;

import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import hugo.weaving.DebugLog;
import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import timber.log.Timber;

/**
 * Returns an observable containing the list of ScanResults returned to a broadcast reciever
 *
 * Created by kriedema on 6/23/16.
 */
@Singleton
public class WifiScanResultsObservable implements WifiScanResultsObservableContract {
    private Subject<List<WifiNetwork>, List<WifiNetwork>> subject = ReplaySubject.create();

    public WifiScanResultsObservable() {
        Timber.v("Created WifiScanResultObservable");
    }

    @Override
    @DebugLog
    @RxLogObservable
    public Observable<List<WifiNetwork>> getScanResults() {
        return subject.asObservable();
    }

    @Override
    @DebugLog
    @RxLogObservable
    public Subject<List<WifiNetwork>, List<WifiNetwork>> getScanResultsSubject() {
        return subject;
    }
}
