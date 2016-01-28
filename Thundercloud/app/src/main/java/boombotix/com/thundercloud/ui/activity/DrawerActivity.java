package boombotix.com.thundercloud.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity {
    @Bind(R.id.left_drawer)
    ListView leftDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<String> list = new ArrayList<String>() {{
            add("A");
            add("B");
            add("C");
        }};
        leftDrawer.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, list));



    }

}
