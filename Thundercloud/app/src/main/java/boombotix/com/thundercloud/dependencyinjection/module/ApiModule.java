package boombotix.com.thundercloud.dependencyinjection.module;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import boombotix.com.thundercloud.BuildConfig;
import boombotix.com.thundercloud.api.SpotifyEndpoint;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
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
    RestAdapter provideRestAdapter(Client client, Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(BuildConfig.SPOTIFY_API_BASE)
                .setClient(client)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    @Singleton
    SpotifyEndpoint provideSpotifyEndpoint(RestAdapter restAdapter) {
        return restAdapter.create(SpotifyEndpoint.class);
    }
}
