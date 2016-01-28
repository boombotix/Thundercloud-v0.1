package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ThundercloudApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kenton on 1/25/16.
 */
@Module
public class ApplicationModule {
    private static final String PREFNAME = "boombotix.com.thundercloud";
    private Application application;

    public ApplicationModule(ThundercloudApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){ return application.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE); }

}
