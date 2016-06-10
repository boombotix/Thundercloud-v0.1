package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import boombotix.com.thundercloud.houndify.model.MusicResponse;
import boombotix.com.thundercloud.houndify.model.MusicSearchCommandNativeData;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by kriedema on 6/10/16.
 */
public class HoundifyJsonDeserializer implements HoundifyDeserializer {

    Gson gson;

    public HoundifyJsonDeserializer(Gson gson) {
        this.gson = gson;
    }

    @DebugLog
    @Override
    public MusicResponse parseMusicResponse(@Nullable String serializedResponse) {
        try {
            return gson.fromJson(serializedResponse, MusicResponse.class);
        } catch (JsonSyntaxException exception){
            Timber.e(exception.getMessage());
            return null;
        }
    }

    @DebugLog
    @Override
    public MusicSearchCommandNativeData parseMusicSearchCommandNativeData(@Nullable String serializedResponse) {
        try {
            return gson.fromJson(serializedResponse, MusicSearchCommandNativeData.class);
        } catch (JsonSyntaxException exception){
            Timber.e(exception.getMessage());
            return null;
        }
    }
}
