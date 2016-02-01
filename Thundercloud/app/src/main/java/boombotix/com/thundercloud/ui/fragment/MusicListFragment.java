package boombotix.com.thundercloud.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.ui.activity.LoginActivity;
import boombotix.com.thundercloud.ui.adapter.YourMusicAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class MusicListFragment extends BaseFragment {

    private static final String ARG_SECTION = "section";
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Inject
    AuthManager authManager;
    @Inject
    SpotifyService spotifyService;

    public MusicListFragment() {
    }


    public static MusicListFragment newInstance(int sectionNumber) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSupportActivity().getActivityComponent().inject(this);
        initView();
    }

    private void initView() {

        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), new ArrayList<Object>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        switch(getArguments().getInt(ARG_SECTION)){
            case 0:
                displayPlaylistContent();
                break;
            case 1:
                displaySongsContent();
                break;
            case 2:
                displayAlbumsContent();
                break;
            case 3:
                displayArtistsContent();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_pager, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void displayArtistsContent() {
    }

    private void displayAlbumsContent() {

    }

    private void displaySongsContent() {

    }

    private void displayPlaylistContent() {
        if(authManager.getUserId() != null) {
            Observable.defer(() -> Observable.just(spotifyService.getPlaylists(authManager.getUserId())))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(playlistSimplePager -> {
                        ArrayList<Object> items = new ArrayList<>();
                        for (PlaylistSimple playlistSimple : playlistSimplePager.items) {
                            items.add(playlistSimple.name);
                        }

                        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), items));


                    });
        }
        else{
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

    }
}