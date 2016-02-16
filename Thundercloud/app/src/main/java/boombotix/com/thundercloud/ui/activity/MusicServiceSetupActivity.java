package boombotix.com.thundercloud.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.musicservice.MusicServiceListFragment;

public class MusicServiceSetupActivity extends BaseActivity
        implements MusicServiceListFragment.OnMusicServiceSelectedListener {

    public static final String FIRST_TIME_SETUP_KEY = "firstTimeSetup";

    private boolean firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_service_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFirstTimeExtra();
        showMusicServiceListFragment();
    }

    /**
     * Reads first time extra and stores the result. Default is false.
     */
    private void getFirstTimeExtra() {
        firstTime = getIntent().getBooleanExtra(FIRST_TIME_SETUP_KEY, false);
    }

    private void showMusicServiceListFragment() {
        //todo get speaker name
        MusicServiceListFragment fragment = MusicServiceListFragment.newInstance("Boombot", firstTime);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onMusicServiceSelected(String name) {
        //todo navigate to info fragment
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMusicServiceFinished() {
        finish();
    }
}
