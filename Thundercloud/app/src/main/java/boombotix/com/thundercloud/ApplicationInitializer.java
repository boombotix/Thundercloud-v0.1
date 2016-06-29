package boombotix.com.thundercloud;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Class to handle the initialization of the application with build or flavor specific requirements
 */
public abstract class ApplicationInitializer {
    void init(Application application){
        JodaTimeAndroid.init(application);

        variantSpecificInitialization(application);
    }

    abstract void variantSpecificInitialization(Application application);
}
