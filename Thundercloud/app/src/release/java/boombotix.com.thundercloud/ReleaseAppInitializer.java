package boombotix.com.thundercloud;

import android.app.Application;

import javax.inject.Inject;

/**
 * ApplicationInitializer that does anything specific to release builds.
 *
 * Empty right now, but it doesn't bring in all of our debug dependencies
 */
public class ReleaseAppInitializer extends ApplicationInitializer {
    @Inject
    public ReleaseAppInitializer() { }

    @Override
    void variantSpecificInitialization(Application application) {
        Timber.plant(new FirebaseTree());
    }
}
