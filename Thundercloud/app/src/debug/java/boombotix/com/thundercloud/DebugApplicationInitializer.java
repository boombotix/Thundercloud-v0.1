package boombotix.com.thundercloud;

import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class DebugApplicationInitializer extends ApplicationInitializer {
    @Inject
    public DebugApplicationInitializer() {}

    @Override
    void variantSpecificInitialization(Application application) {
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(application);
    }
}
