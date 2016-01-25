package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ThundercloudApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kenton on 1/25/16.
 */
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(ThundercloudApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

}
