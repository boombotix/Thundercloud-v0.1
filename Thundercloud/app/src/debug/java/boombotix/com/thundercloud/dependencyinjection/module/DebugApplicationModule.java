package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ApplicationInitializer;
import boombotix.com.thundercloud.DebugApplicationInitializer;
import boombotix.com.thundercloud.ThundercloudApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Debug version of our application module. Should be used to resolve dependencies that do something
 * special on debug builds, like classes that make heavy use of logging libraries, etc.
 *
 * Created by kriedema on 6/28/16.
 */
@Module(includes = BluetoothModule.class)
public class DebugApplicationModule extends ApplicationModule{
    public DebugApplicationModule(ThundercloudApplication application) {
        super(application);
    }

    @Provides
    @Singleton
    ApplicationInitializer providesApplicaitonInitializer(){
        return new DebugApplicationInitializer();
    }
}
