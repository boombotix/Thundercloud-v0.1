package boombotix.com.thundercloud.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.model.music.MusicDetailListItem;
import boombotix.com.thundercloud.model.music.Song;
import boombotix.com.thundercloud.ui.adapter.ArtistDetailsAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Artist details screen
 *
 */
public class ArtistDetailsFragment extends BaseFragment {
    public static final String ARG_ARTIST_ID = "ARTIST_ID";
    @Inject
    SpotifyService spotifyService;
    @Inject
    AuthManager authManager;
    private String artistId;
    private final int itemAmount = 3;
    @Bind(R.id.details_recyclerview)
    RecyclerView detailsRecycler;

    public ArtistDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActivity().getActivityComponent().inject(this);
    }

    public static ArtistDetailsFragment newInstance(String artistId) {
        ArtistDetailsFragment fragment  = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        ButterKnife.bind(this, view);
        artistId = getArguments().getString(ARG_ARTIST_ID);
        getArtistDetails();
        return view;
    }

    private void getArtistDetails(){

        Observable
                .defer(() -> Observable.zip(Observable.just(spotifyService.getArtistAlbums(artistId)),
                        Observable.just(spotifyService.getArtistTopTrack(artistId, "ES")),
                        (albums, tracks) -> new Pair<>(albums, tracks)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pagerTracksPair -> {
                            ArrayList<MusicDetailListItem> musicDetailListItems = new ArrayList<>();

                            //get all the songs
                            musicDetailListItems.addAll(createSongs(pagerTracksPair));
                            //get all the albums
                            musicDetailListItems.addAll(createAlbums(pagerTracksPair));

                            detailsRecycler.setAdapter(new ArtistDetailsAdapter(getActivity(), musicDetailListItems));
                            detailsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        });
    }

    private ArrayList<MusicDetailListItem> createAlbums(Pair<Pager<Album>, Tracks> pagerTracksPair) {
        ArrayList<MusicDetailListItem> items = new ArrayList<>();

        //set songs header
        MusicDetailListItem songsHeader =  new MusicDetailListItem();
        songsHeader.setupNewHeader("Top Songs");
        items.add(songsHeader);

        List<Album> albums = pagerTracksPair.first.items;
        for(int i = 0; i < itemAmount && albums.get(i) != null; i++ ){
            MusicDetailListItem musicDetailListItem = new MusicDetailListItem();
            boombotix.com.thundercloud.model.music.Album album = new boombotix.com.thundercloud.model.music.Album();
            album.name = albums.get(i).name;
            album.artworkUrl = albums.get(i).images.get(0).url;
            musicDetailListItem.musicListItem = album;
            musicDetailListItem.type = MusicDetailListItem.TYPE_ALBUM;
            items.add(musicDetailListItem);
        }

        //create link for more songs
        MusicDetailListItem tracksLink = new MusicDetailListItem();
        tracksLink.setupNewLink("See All Songs");
        items.add(tracksLink);

        return items;
    }

    private ArrayList<MusicDetailListItem> createSongs(Pair<Pager<Album>, Tracks> pagerTracksPair) {
        ArrayList<MusicDetailListItem> items = new ArrayList<>();

        //set albums header
        MusicDetailListItem albumsHeader =  new MusicDetailListItem();
        albumsHeader.setupNewHeader("Albums");
        items.add(albumsHeader);

        List<Track> tracks = pagerTracksPair.second.tracks;
        for(int i = 0; i < itemAmount && tracks.get(i) != null; i++ ){
            MusicDetailListItem musicDetailListItem = new MusicDetailListItem();
            Song song = new Song();
            song.name = tracks.get(i).name;
            song.artist = TextUtils.join(",", tracks.get(i).artists);
            song.album = tracks.get(i).album.name;
            musicDetailListItem.musicListItem = song;
            musicDetailListItem.type = MusicDetailListItem.TYPE_SONG;
            items.add(musicDetailListItem);
        }

        //create link for more albums
        MusicDetailListItem albumsLink = new MusicDetailListItem();
        albumsLink.setupNewLink("See All Albums");
        items.add(albumsLink);

        return items;
    }

}
