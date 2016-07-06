package boombotix.com.thundercloud.dependencyinjection.module;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.api.SpotifySearchEndpoint;
import boombotix.com.thundercloud.api.SpotifyTrackEndpoint;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Returns all of our API dependencies with more verbose log levels, and Stetho and stuff
 *
 * Created by kenton on 1/24/16.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        // this version of stetho is marked deprecated, but it's stable and it's the last release
        // that supports OkHttp version 2
        client.networkInterceptors().add(new StethoInterceptor());
        
        return client;
    }

    @Provides
    @Singleton
    @Named("SpotifyApiRestAdapter")
    RestAdapter provideApiRestAdapter(Client client, Gson gson) {
        return restAdapterWithUrl(client, gson, BuildConfig.SPOTIFY_API_BASE);
    }

    @Provides
    @Singleton
    @Named("SpotifyAuthRestAdapter")
    RestAdapter provideAuthRestAdapter(Client client, Gson gson) {
        return restAdapterWithUrl(client, gson, BuildConfig.SPOTIFY_AUTH_URL);
    }

    private RestAdapter restAdapterWithUrl(Client client, Gson gson, String baseUrl){
        return new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(client)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    @Singleton
    SpotifyAuthenticationEndpoint provideSpotifyAuthEndpoint(@Named("SpotifyAuthRestAdapter") RestAdapter restAdapter) {
        return restAdapter.create(SpotifyAuthenticationEndpoint.class);
    }

    @Provides
    @Singleton
    SpotifyTrackEndpoint providesSpotifyTrackEndpoint(@Named("SpotifyApiRestAdapter") RestAdapter restAdapter){
        return restAdapter.create(SpotifyTrackEndpoint.class);
    }

    @Provides
    @Singleton
    SpotifySearchEndpoint provideSpotifySearchEndpoint(@Named("SpotifyApiRestAdapter") RestAdapter restAdapter){
        return restAdapter.create(SpotifySearchEndpoint.class);
    }
}
