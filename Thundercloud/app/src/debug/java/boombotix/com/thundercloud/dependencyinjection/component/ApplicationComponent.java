package boombotix.com.thundercloud.dependencyinjection.component;

import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.dependencyinjection.ThundercloudGraph;
import boombotix.com.thundercloud.dependencyinjection.module.ApiModule;
import boombotix.com.thundercloud.dependencyinjection.module.BluetoothModule;
import boombotix.com.thundercloud.dependencyinjection.module.DebugApplicationModule;
import boombotix.com.thundercloud.dependencyinjection.module.MockBluetoothModule;
import boombotix.com.thundercloud.dependencyinjection.module.RepositoryModule;
import dagger.Component;

/**
 * Application level injection class (Component). The lifetime of this component is the lifetime of
 * the application, and any global modules should be defined as part of this module.
 * <p>
 * For Modules/injections that only need to live through a given activity lifecycle (which should in
 * theory be anything which is not strictly global), see {@link ActivityComponent}
 * <p>
 * Created by kenton on 1/24/16.
 */
@Singleton
@Component(modules =
        {
                DebugApplicationModule.class,
                RepositoryModule.class,
                ApiModule.class,
                BluetoothModule.class
        }
)
public interface ApplicationComponent extends ThundercloudGraph {

    final class Initializer {

        public static ThundercloudGraph init(ThundercloudApplication application) {
            DaggerApplicationComponent.Builder builder = DaggerApplicationComponent.builder()
                    .debugApplicationModule(new DebugApplicationModule(application))
                    .apiModule(new ApiModule())
                    .repositoryModule(new RepositoryModule(application));

            if(BuildConfig.MOCK_BLUETOOTH_CONNECTION){
                builder.bluetoothModule(new MockBluetoothModule());
            } else {
                builder.bluetoothModule(new BluetoothModule());
            }

            return builder.build();
        }

        private Initializer() {
            // Block instantiation
        }
    }
}
