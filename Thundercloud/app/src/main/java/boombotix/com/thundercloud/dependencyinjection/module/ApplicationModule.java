package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.authentication.AuthManager;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;
import boombotix.com.thundercloud.houndify.response.HoundifyDeserializer;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.houndify.response.HoundifyJsonDeserializer;
import boombotix.com.thundercloud.houndify.response.HoundifySdkModelExtractor;
import boombotix.com.thundercloud.houndify.response.HoundifyModelExtractor;
import boombotix.com.thundercloud.playback.LocalPlaybackQueue;
import boombotix.com.thundercloud.playback.LocalQueueControls;
import boombotix.com.thundercloud.playback.MockPlayer;
import boombotix.com.thundercloud.playback.MusicControls;
import boombotix.com.thundercloud.playback.MusicPlayerProvider;
import boombotix.com.thundercloud.playback.PlaybackQueue;
import boombotix.com.thundercloud.playback.SlackerPlayer;
import boombotix.com.thundercloud.playback.SpotifyPlayer;
import boombotix.com.thundercloud.wifi.WifiScanResultsObservable;
import boombotix.com.thundercloud.wifi.WifiScanResultsObservableContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kenton on 1/25/16.
 */
@Module
public class ApplicationModule {
    private static final String PREFNAME = "boombotix.com.thundercloud";
    private Application application;

    public ApplicationModule(ThundercloudApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){ return application.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE); }

    @Provides
    @Singleton
    HoundifyRequestTransformer provideHoundifyRequestAdapter() { return new HoundifyRequestTransformer(); }

    @Provides
    @Singleton
    HoundifyModelExtractor provideHoundifyModelExtractor() { return new HoundifySdkModelExtractor(); }

    @Provides
    @Singleton
    HoundifyDeserializer provideHoundifyModelDeserializer(Gson gson) { return new HoundifyJsonDeserializer(gson); }

    @Provides
    @Singleton
    HoundifyResponseParser provideHoundifyHelper(HoundifyDeserializer houndifyDeserializer, HoundifyModelExtractor houndifyModelExtractor, HoundifyRequestTransformer houndifyRequestTransformer){
        return new HoundifyResponseParser(houndifyDeserializer, houndifyModelExtractor, houndifyRequestTransformer);
    }

    @Provides
    @Singleton
    PlaybackQueue providesPlaybackQueue(){
        return new LocalPlaybackQueue();
    }

    @Provides
    @Singleton
    MusicControls providesMusicControls(PlaybackQueue playbackQueue, MusicPlayerProvider musicPlayerProvider) {
        return new LocalQueueControls(playbackQueue, musicPlayerProvider);
    }

    @Provides
    @Singleton
    MusicPlayerProvider providesMusicPlayer(SpotifyPlayer spotifyPlayer, SlackerPlayer slackerPlayer, MockPlayer mockPlayer){
        return new MusicPlayerProvider(spotifyPlayer, slackerPlayer, mockPlayer);
    }

    @Provides
    @Singleton
    SpotifyPlayer providesSpotifyPlayer(Application application, AuthManager authManager){
        return new SpotifyPlayer(application, authManager);
    }

    @Provides
    @Singleton
    SlackerPlayer providesSlackerPlayer(){
        return new SlackerPlayer();
    }

    @Provides
    @Singleton
    MockPlayer providesMockplayer(){
        return new MockPlayer();
    }

    @Provides
    @Singleton
    WifiScanResultsObservableContract providesWifiScanResultsObservable() {
        return new WifiScanResultsObservable();
    }
}
