package boombotix.com.thundercloud.dependencyinjection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import boombotix.com.thundercloud.ThundercloudApplication;
import boombotix.com.thundercloud.houndify.response.HoundifyDeserializer;
import boombotix.com.thundercloud.houndify.response.HoundifyResponseParser;
import boombotix.com.thundercloud.houndify.response.HoundifyJsonDeserializer;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestAdapter;
import boombotix.com.thundercloud.houndify.response.HoundifySdkModelExtractor;
import boombotix.com.thundercloud.houndify.response.HoundifyModelExtractor;
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
    HoundifyRequestAdapter provideHoundifyRequestAdapter() { return new HoundifyRequestAdapter(); }

    @Provides
    @Singleton
    HoundifyModelExtractor provideHoundifyModelExtractor() { return new HoundifySdkModelExtractor(); }

    @Provides
    @Singleton
    HoundifyDeserializer provideHoundifyModelDeserializer(Gson gson) { return new HoundifyJsonDeserializer(gson); }

    @Provides
    @Singleton
    HoundifyResponseParser provideHoundifyHelper(HoundifyDeserializer houndifyDeserializer, HoundifyModelExtractor houndifyModelExtractor, HoundifyRequestAdapter houndifyRequestAdapter){
        return new HoundifyResponseParser(houndifyDeserializer, houndifyModelExtractor, houndifyRequestAdapter);
    }
}
