package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {
    @SerializedName("albums")
    private Albums albums;

    @SerializedName("artists")
    private Artists artists;

    @SerializedName("tracks")
    private Tracks tracks;

    @SerializedName("playlists")
    private Playlists playlists;

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public Playlists getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlists playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "albums=" + albums +
                ", artists=" + artists +
                ", tracks=" + tracks +
                ", playlists=" + playlists +
                '}';
    }
}
