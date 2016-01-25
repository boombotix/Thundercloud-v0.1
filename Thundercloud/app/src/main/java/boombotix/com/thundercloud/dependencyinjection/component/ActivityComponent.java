package boombotix.com.thundercloud.dependencyinjection.component;

import boombotix.com.thundercloud.dependencyinjection.graph.ActivityGraph;
import boombotix.com.thundercloud.dependencyinjection.module.ActivityModule;
import boombotix.com.thundercloud.ui.activity.MainActivity;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import dagger.Component;

/**
 * Activity level injection class (Component). The lifetime of this component
 * is the lifetime of a single activity, and any non-global modules should
 * be defined as part of this module.
 *
 * For Modules/injections that need to live globally see {@link ApplicationComponent}
 *
 * Created by kenton on 1/24/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends ActivityGraph{

    final class Initializer {
        public static ActivityComponent init(BaseActivity activity) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(activity.getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .build();
        }

        private Initializer() {
            // Block instantiation
        }
    }

    void inject(MainActivity mainActivity);
}
