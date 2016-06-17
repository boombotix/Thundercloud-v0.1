package boombotix.com.thundercloud.playback;

import boombotix.com.thundercloud.model.music.MusicListItem;

/**
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
//        if(BuildConfig.DEBUG)
//            return mockPlayer;

        switch (musicListItem.getService()){
            case Spotify:
                return this.spotifyPlayer;
            case Slacker:
                return this.slackerPlayer;
            default:
                throw new RuntimeException("Player not implimented");
        }
    }
}
