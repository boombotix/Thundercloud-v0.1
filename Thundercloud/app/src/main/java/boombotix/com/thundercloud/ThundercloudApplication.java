package boombotix.com.thundercloud;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

import boombotix.com.thundercloud.dependencyinjection.component.ApplicationComponent;
import timber.log.Timber;

/**
 * Application extension for Thundercloud app. Responsible for global initializations.
 *
 * Created by kenton on 1/24/16.
 */
public class ThundercloudApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        buildComponent();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**
     * Build the application level Dagger component
     */
    private void buildComponent() {
        this.applicationComponent = ApplicationComponent.Initializer.init(this);
    }
}
