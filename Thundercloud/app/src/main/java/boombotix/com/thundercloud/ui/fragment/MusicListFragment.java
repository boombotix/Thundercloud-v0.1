package boombotix.com.thundercloud.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.AuthRefreshResponse;
import boombotix.com.thundercloud.ui.activity.LoginActivity;
import boombotix.com.thundercloud.ui.adapter.YourMusicAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.SavedAlbum;
import kaaes.spotify.webapi.android.models.SavedTrack;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class MusicListFragment extends BaseFragment implements AuthManager.AuthRefreshRespCallback {
    private final String TAG = "MusicListFragment";
    private static final String ARG_SECTION = "section";
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Inject
    AuthManager authManager;
    @Inject
    SpotifyService spotifyService;
    @Inject
    SpotifyApi spotifyApi;

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
        if(authManager.getUserId() != null) {
            if (authManager.isExpired()) {
                authManager.refreshAuthToken(this);
            }
            else{
                initView();
            }
        }
        else{
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void initView() {
        spotifyApi.setAccessToken(authManager.getAccessToken());
        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), new ArrayList<>()));
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
        View rootView = inflater.inflate(R.layout.fragment_music_pager_list, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void displayArtistsContent() {
        Observable.defer(() -> Observable.just(spotifyService.getFollowedArtists()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArtistsCursorPager>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArtistsCursorPager artistsCursorPager) {
                        ArrayList<Pair<String, String>> items = new ArrayList<>();
                        for (Artist artist : artistsCursorPager.artists.items) {
                            items.add(new Pair<>(artist.name, TextUtils.join(", ", artist.genres)));
                        }

                        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), items));
                    }
                });
    }

    private void displayAlbumsContent() {
        Observable.defer(() -> Observable.just(spotifyService.getMySavedAlbums()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pager<SavedAlbum>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pager<SavedAlbum> savedAlbumPager) {
                        ArrayList<Pair<String, String>> items = new ArrayList<>();
                        for (SavedAlbum savedAlbum : savedAlbumPager.items) {
                            items.add(new Pair<>(savedAlbum.album.name,
                                    pluralize(getSupportActivity().getString(R.string.song),
                                            savedAlbum.album.tracks.items.size())));
                        }

                        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), items));
                    }
                });
    }

    private void displaySongsContent() {
        Observable.defer(() -> Observable.just(spotifyService.getMySavedTracks()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pager<SavedTrack>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pager<SavedTrack> savedTrackPager) {
                            ArrayList<Pair<String, String>> items = new ArrayList<>();
                            for (SavedTrack savedTrack : savedTrackPager.items) {
                                items.add(new Pair<>(savedTrack.track.name,
                                        String.valueOf(savedTrack.track.duration_ms/1000)));
                            }

                            recyclerView.setAdapter(new YourMusicAdapter(getActivity(), items));
                        }
                });
    }

    private void displayPlaylistContent() {
        Observable.defer(() -> Observable.just(spotifyService.getPlaylists(authManager.getUserId())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pager<PlaylistSimple>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Pager<PlaylistSimple> playlistSimplePager) {
                        ArrayList<Pair<String, String>> items = new ArrayList<>();
                        for (PlaylistSimple playlistSimple : playlistSimplePager.items) {
                            items.add(new Pair<>(playlistSimple.name,
                                    pluralize(getSupportActivity().getString(R.string.song),
                                            playlistSimple.tracks.total)));
                        }

                        recyclerView.setAdapter(new YourMusicAdapter(getActivity(), items));
                    }
                });
    }

    @Override
    public void onSuccess(AuthRefreshResponse authRefreshResponse) {
        initView();
    }

    @Override
    public void onError(Throwable error) {
        Log.e(TAG, error.getMessage());
        error.printStackTrace();
    }

    private String pluralize(String s, int c){
        if(c < 2) return String.valueOf(c) + " " + s;
        return String.valueOf(c) + " " + s + "s";
    }
}
