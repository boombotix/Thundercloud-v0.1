package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Application;

import dagger.Module;

/**
 * Dagger module for defining injection graph for ORMLite
 * database repositories.
 *
 * Created by kenton on 1/24/16.
 */
@Module
public class RepositoryModule {

    private Application application;

    public RepositoryModule(Application application) {
        this.application = application;
    }
}
