package boombotix.com.thundercloud.houndify;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.hound.android.sdk.util.HoundRequestInfoFactory;
import com.hound.core.model.sdk.HoundRequestInfo;
import com.hound.core.model.sdk.HoundResponse;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.houndify.model.HoundifyResponse;

/**
 * Created by jsaucedo on 2/16/16.
 */
@Singleton
public class HoundifyHelper {

    @Inject
    public HoundifyHelper() {}

    public HoundRequestInfo getHoundRequestInfo(Context context) {
        final HoundRequestInfo requestInfo = HoundRequestInfoFactory.getDefault(context);

        requestInfo.setUserId("User ID");
        requestInfo.setRequestId(UUID.randomUUID().toString());

        return requestInfo;
    }

    public HoundifyResponse parseResponse(HoundResponse response){
        
        JsonNode node = response.getResults().get(0).getJsonNode();

        JsonNode nativeData = node.get("NativeData");

        String jsonString = nativeData.toString();

        return   new Gson().fromJson(jsonString, HoundifyResponse.class);
    }
}
