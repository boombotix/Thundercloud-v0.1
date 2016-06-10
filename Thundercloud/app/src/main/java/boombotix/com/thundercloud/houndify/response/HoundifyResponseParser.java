package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.core.model.sdk.HoundResponse;

import boombotix.com.thundercloud.houndify.model.MusicResponse;
import boombotix.com.thundercloud.houndify.model.MusicSearchCommandNativeData;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestAdapter;

/**
 * Created by jsaucedo on 2/16/16.
 */
public class HoundifyResponseParser {

    HoundifyDeserializer houndifyDeserializer;
    HoundifyModelExtractor houndifyModelExtractor;
    HoundifyRequestAdapter houndifyRequestAdapter;

    public HoundifyResponseParser(HoundifyDeserializer houndifyDeserializer, HoundifyModelExtractor houndifyModelExtractor, HoundifyRequestAdapter houndifyRequestAdapter) {
        this.houndifyDeserializer = houndifyDeserializer;
        this.houndifyModelExtractor = houndifyModelExtractor;
        this.houndifyRequestAdapter = houndifyRequestAdapter;
    }

    @Nullable
    public MusicResponse parseMusicResponse(@Nullable HoundResponse response){

        JsonNode nativeData = houndifyModelExtractor.extractNativeData(response);

        if(nativeData == null) return null;

        houndifyRequestAdapter.setConversationState(houndifyModelExtractor.extractConversationState(response));

        return houndifyDeserializer.parseMusicResponse(nativeData.toString());
    }

    @Nullable
    public MusicSearchCommandNativeData parseMusicSearchResponse(@Nullable HoundResponse response){

        JsonNode nativeData = houndifyModelExtractor.extractNativeData(response);

        if(nativeData == null) return null;

        houndifyRequestAdapter.setConversationState(houndifyModelExtractor.extractConversationState(response));

        return houndifyDeserializer.parseMusicSearchCommandNativeData(nativeData.toString());
    }
}
