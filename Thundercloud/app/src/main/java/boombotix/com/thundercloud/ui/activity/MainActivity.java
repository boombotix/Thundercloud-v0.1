package boombotix.com.thundercloud.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Inject
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getActivityComponent().inject(this);
    }

}
