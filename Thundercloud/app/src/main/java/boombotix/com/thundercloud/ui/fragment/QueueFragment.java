package boombotix.com.thundercloud.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.adapter.QueueAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Shows a list of all playing songs
 *
 * @author Theo Kanning
 */
public class QueueFragment extends Fragment {

    @Bind(R.id.queue)
    RecyclerView queueRecyclerView;

    public static QueueFragment newInstance() {
        QueueFragment fragment = new QueueFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_queue, container, false);
        ButterKnife.bind(this, view);

        initQueue();
        return view;
    }

    private void initQueue() {
        Pair<String, String> song = new Pair<>("Sandstorm", "Darude");
        List<Pair<String, String>> songs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            songs.add(song);
        }
        queueRecyclerView.setAdapter(new QueueAdapter(songs));
        queueRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
