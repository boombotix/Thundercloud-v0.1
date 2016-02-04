package boombotix.com.thundercloud.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import boombotix.com.thundercloud.R;

/**
 * Enables bluetooth, then shows the user a list of available Boombots. Finishes with selected UUID
 * as a result.
 *
 * @author Theo Kanning
 */
public class SpeakerPairingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_pairing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
