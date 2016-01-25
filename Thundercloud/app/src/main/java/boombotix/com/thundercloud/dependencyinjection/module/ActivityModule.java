package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Activity;

import boombotix.com.thundercloud.dependencyinjection.component.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kenton on 1/25/16.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }
}
