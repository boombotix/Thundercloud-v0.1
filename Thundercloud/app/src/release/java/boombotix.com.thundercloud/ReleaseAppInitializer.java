package boombotix.com.thundercloud;

import android.app.Application;

import javax.inject.Inject;

/**
 * Created by kriedema on 6/28/16.
 */
public class ReleaseAppInitializer extends ApplicationInitializer {
    @Inject
    public ReleaseAppInitializer() { }

    @Override
    void variantSpecificInitialization(Application application) {

    }
}
