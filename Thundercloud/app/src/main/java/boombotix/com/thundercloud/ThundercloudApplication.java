package boombotix.com.thundercloud;

import android.app.Application;

import javax.inject.Inject;

import boombotix.com.thundercloud.dependencyinjection.ThundercloudGraph;
import boombotix.com.thundercloud.dependencyinjection.component.ApplicationComponent;

/**
 * Application extension for Thundercloud app. Responsible for global initializations.
 *
 * Created by kenton on 1/24/16.
 */
public class ThundercloudApplication extends Application {

    private static ThundercloudApplication application;
    ThundercloudGraph thundercloudGraph;

    @Inject
    ApplicationInitializer applicationInitializer;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        buildComponentAndInject();

        applicationInitializer.init(this);
    }

    public static ThundercloudApplication instance(){
        return application;
    }

    public ThundercloudGraph getThundercloudGraph() {
        return this.thundercloudGraph;
    }

    /**
     * Build the application level Dagger component
     */
    private void buildComponentAndInject() {
        this.thundercloudGraph = ApplicationComponent.Initializer.init(this);
        thundercloudGraph.inject(this);
    }
}
