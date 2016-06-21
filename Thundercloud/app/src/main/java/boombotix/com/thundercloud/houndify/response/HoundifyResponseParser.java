package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.core.model.sdk.HoundResponse;

import boombotix.com.thundercloud.houndify.model.MusicResponse;
import boombotix.com.thundercloud.houndify.model.MusicSearchResultsNativeData;
import boombotix.com.thundercloud.houndify.request.HoundifyRequestTransformer;

/**
 * Created by jsaucedo on 2/16/16.
 */
public class HoundifyResponseParser {

    HoundifyDeserializer houndifyDeserializer;
    HoundifyModelExtractor houndifyModelExtractor;
    HoundifyRequestTransformer houndifyRequestTransformer;

    public HoundifyResponseParser(HoundifyDeserializer houndifyDeserializer, HoundifyModelExtractor houndifyModelExtractor, HoundifyRequestTransformer houndifyRequestTransformer) {
        this.houndifyDeserializer = houndifyDeserializer;
        this.houndifyModelExtractor = houndifyModelExtractor;
        this.houndifyRequestTransformer = houndifyRequestTransformer;
    }

    @Nullable
    public MusicResponse parseMusicResponse(@Nullable HoundResponse response){

        JsonNode nativeData = houndifyModelExtractor.extractNativeData(response);

        if(nativeData == null) return null;

        houndifyRequestTransformer.setConversationState(houndifyModelExtractor.extractConversationState(response));

        return houndifyDeserializer.parseMusicResponse(nativeData.toString());
    }

    @Nullable
    public MusicSearchResultsNativeData parseMusicSearchResponse(@Nullable HoundResponse response){

        JsonNode nativeData = houndifyModelExtractor.extractNativeData(response);

        if(nativeData == null) return null;

        houndifyRequestTransformer.setConversationState(houndifyModelExtractor.extractConversationState(response));

        return houndifyDeserializer.parseMusicSearchCommandNativeData(nativeData.toString());
    }
}
