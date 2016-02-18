package boombotix.com.thundercloud.houndify;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.hound.android.fd.Houndify;
import com.hound.android.sdk.util.HoundRequestInfoFactory;
import com.hound.core.model.sdk.HoundRequestInfo;
import com.hound.core.model.sdk.HoundResponse;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import boombotix.com.thundercloud.houndify.model.HoundifyResponse;
import boombotix.com.thundercloud.houndify.model.Track;

/**
 * Created by jsaucedo on 2/16/16.
 */
@Singleton
public class HoundifyHelper {
    @Inject
    Gson gson;

    @Inject
    public HoundRequestInfo getHoundRequestInfo(Context context) {
        final HoundRequestInfo requestInfo = HoundRequestInfoFactory.getDefault(context);

        requestInfo.setUserId("User ID");
        requestInfo.setRequestId(UUID.randomUUID().toString());

        return requestInfo;
    }

    @Inject
    public HoundifyResponse parseResponse(HoundResponse response){
        HoundifyResponse houndifyResponse =  new Gson().fromJson(response.getResults().get(0)
                .getJsonNode().get("NativeData").toString(), HoundifyResponse.class);
        return houndifyResponse;
    }
}
