package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import boombotix.com.thundercloud.houndify.model.MusicResponse;
import boombotix.com.thundercloud.houndify.model.MusicSearchResultsNativeData;

/**
 * Created by kriedema on 6/10/16.
 */
public interface HoundifyDeserializer {

    @Nullable
    MusicResponse parseMusicResponse(@Nullable String serializedResponse);

    @Nullable
    MusicSearchResultsNativeData parseMusicSearchCommandNativeData(@Nullable String serializedResponse);
}
