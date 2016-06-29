package boombotix.com.thundercloud;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * ApplicationInitializer that drags in timber and stetho
 */
@Singleton
public class DebugApplicationInitializer extends ApplicationInitializer {
    @Inject
    public DebugApplicationInitializer() {}

    @Override
    void variantSpecificInitialization(Application application) {
        Timber.plant(new Timber.DebugTree());
        Timber.plant(new StethoTree());
        Stetho.initializeWithDefaults(application);
    }
}
