package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ApplicationInitializer;
import boombotix.com.thundercloud.ReleaseAppInitializer;
import boombotix.com.thundercloud.ThundercloudApplication;
import dagger.Module;
import dagger.Provides;

/**
 * ApplicationModule that doesn't bring in any debugging dependencies
 *
 * Created by kriedema on 6/29/16.
 */
@Module(includes = BluetoothModule.class)
public class ProductionApplicationModule extends ApplicationModule {
    public ProductionApplicationModule(ThundercloudApplication application) {
        super(application);
    }

    @Provides
    @Singleton
    ApplicationInitializer providesApplicaitonInitializer(){
        return new ReleaseAppInitializer();
    }
}
