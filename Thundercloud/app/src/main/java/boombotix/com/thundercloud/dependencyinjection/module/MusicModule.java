package boombotix.com.thundercloud.dependencyinjection.module;

import javax.inject.Singleton;

import boombotix.com.thundercloud.music.MusicController;
import boombotix.com.thundercloud.music.MusicPlayer;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Theo Kanning on 2/11/2016.
 */
@Module
public class MusicModule {
    @Provides
    @Singleton
    MusicPlayer provideMusicPlayer(){
        return new MusicPlayer();
    }

    @Provides
    @Singleton
    MusicController provideMusicController(MusicPlayer musicPlayer){
        return new MusicController(musicPlayer);
    }
}
