package boombotix.com.thundercloud.ui.model;

import android.support.annotation.IntDef;

import boombotix.com.thundercloud.model.search.spotify.Album;
import boombotix.com.thundercloud.model.search.spotify.Artist;
import boombotix.com.thundercloud.model.search.spotify.Playlist;
import boombotix.com.thundercloud.model.search.spotify.Track;

public class SearchResultRowModel {
    private String header;
    private Artist artist;
    private Album album;
    private Track track;
    private Playlist playlist;

    @IntDef({
            SEARCH_RESULTS_ARTIST_HEADER,
            SEARCH_RESULTS_ARTIST_ITEM,
            SEARCH_RESULTS_ALBUM_HEADER,
            SEARCH_RESULTS_ALBUM_ITEM,
            SEARCH_RESULTS_TRACK_HEADER,
            SEARCH_RESULTS_TRACK_ITEM,
            SEARCH_RESULTS_PLAYLIST_HEADER,
            SEARCH_RESULTS_PLAYLIST_ITEM
    })
    public @interface SearchResultView {}
    public static final int SEARCH_RESULTS_ARTIST_HEADER = 0;
    public static final int SEARCH_RESULTS_ARTIST_ITEM = 1;
    public static final int SEARCH_RESULTS_ALBUM_HEADER = 2;
    public static final int SEARCH_RESULTS_ALBUM_ITEM = 3;
    public static final int SEARCH_RESULTS_TRACK_HEADER = 4;
    public static final int SEARCH_RESULTS_TRACK_ITEM = 5;
    public static final int SEARCH_RESULTS_PLAYLIST_HEADER = 6;
    public static final int SEARCH_RESULTS_PLAYLIST_ITEM = 7;

    private SearchResultRowModel(String header, Artist artist, Album album, Track track, Playlist playlist) {
        this.header = header;
        this.artist = artist;
        this.album = album;
        this.track = track;
        this.playlist = playlist;
    }

    public static SearchResultRowModel header(String header){
        return new SearchResultRowModel(header, null, null, null, null);
    }

    public static SearchResultRowModel artist(Artist artist){
        return new SearchResultRowModel(null, artist, null, null, null);
    }

    public static SearchResultRowModel album(Album album){
        return new SearchResultRowModel(null, null, album, null, null);
    }

    public static SearchResultRowModel track(Track track){
        return new SearchResultRowModel(null, null, null, track, null);
    }

    public static SearchResultRowModel playlist(Playlist playlist){
        return new SearchResultRowModel(null, null, null, null, playlist);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
