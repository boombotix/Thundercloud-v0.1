package boombotix.com.thundercloud.dependencyinjection.module;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;
import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.api.SpotifyAuthenticationEndpoint;
import boombotix.com.thundercloud.api.SpotifyTrackEndpoint;
import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * API module that doesn't add logging interceptors
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
        return new OkHttpClient();
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
                .setLogLevel(RestAdapter.LogLevel.NONE)
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
    SpotifyApi provideSpotifyApi() {
        return new SpotifyApi();
    }

    @Provides
    @Singleton
    SpotifyService provideSpotifyService(SpotifyApi spotifyApi) {
        return spotifyApi.getService();
    }
}
