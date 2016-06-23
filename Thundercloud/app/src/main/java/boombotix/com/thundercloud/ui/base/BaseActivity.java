package boombotix.com.thundercloud.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.dependencyinjection.component.ActivityComponent;
import boombotix.com.thundercloud.dependencyinjection.component.ApplicationComponent;

/**
 * Created by kenton on 1/24/16.
 */
public class BaseActivity extends AppCompatActivity {

    ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareDagger();
    }

    public ActivityComponent getActivityComponent() {
        return this.activityComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return ((ThundercloudApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Prepares activity and application level object graphs
     */
    private void prepareDagger() {
        buildComponent();
    }

    /**
     * Builds the activity level Dagger component
     */
    private void buildComponent() {
        this.activityComponent = ActivityComponent.Initializer.init(this);
    }
}
