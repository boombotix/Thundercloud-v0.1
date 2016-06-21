package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
 * Returns a music player based on the musicListItem we are attempting to play
 *
 * Created by kriedema on 6/14/16.
 */
public class MusicPlayerProvider {

    private MusicPlayer spotifyPlayer;
    private MusicPlayer slackerPlayer;
    private MusicPlayer mockPlayer;

    public MusicPlayerProvider(SpotifyPlayer spotifyPlayer, SlackerPlayer slackerPlayer,  MockPlayer mockPlayer){
        this.spotifyPlayer = spotifyPlayer;
        this.slackerPlayer = slackerPlayer;
        this.mockPlayer = mockPlayer;
    }

    public MusicPlayer getMusicPlayer(MusicListItem musicListItem){
        switch (musicListItem.getService()){
            case Spotify:
                return this.spotifyPlayer;
            case Slacker:
                return this.slackerPlayer;
            default:
                throw new RuntimeException("Player not implemented");
        }
    }
}
