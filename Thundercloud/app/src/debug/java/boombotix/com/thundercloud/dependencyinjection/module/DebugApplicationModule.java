package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ApplicationInitializer;
import boombotix.com.thundercloud.DebugApplicationInitializer;
import boombotix.com.thundercloud.ThundercloudApplication;
import dagger.Module;
import dagger.Provides;

/**
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
