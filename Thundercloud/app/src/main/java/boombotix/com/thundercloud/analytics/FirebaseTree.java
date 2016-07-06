package boombotix.com.thundercloud.analytics;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

public class FirebaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        FirebaseCrash.log(message);

        if (t != null) {
            FirebaseCrash.report(t);
        }
    }
}