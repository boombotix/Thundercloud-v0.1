package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
 * Returns a music audioEngine based on the musicListItem we are attempting to play
 *
 * Created by kriedema on 6/14/16.
 */
public class AudioEngineProvider {

    private AudioEngine spotifyPlayer;
    private AudioEngine slackerPlayer;
    private AudioEngine mockPlayer;

    public AudioEngineProvider(SpotifyEngine spotifyEngine, SlackerEngine slackerEngine, MockEngine mockEngine){
        this.spotifyPlayer = spotifyEngine;
        this.slackerPlayer = slackerEngine;
        this.mockPlayer = mockEngine;
    }

    public AudioEngine getMusicPlayer(MusicListItem musicListItem){
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
