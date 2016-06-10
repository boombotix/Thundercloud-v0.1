package boombotix.com.thundercloud.houndify.request;

import android.content.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.android.sdk.util.HoundRequestInfoFactory;
import com.hound.core.model.sdk.HoundRequestInfo;

import java.util.UUID;

import timber.log.Timber;

/**
 * Created by kriedema on 6/10/16.
 */
public class HoundifyRequestAdapter {

    private JsonNode conversationState;

    public HoundRequestInfo getHoundRequestInfo(Context context) {
        final HoundRequestInfo requestInfo = HoundRequestInfoFactory.getDefault(context);

        requestInfo.setUserId("User ID");
        requestInfo.setRequestId(UUID.randomUUID().toString());

        if(conversationState != null){
            requestInfo.setConversationState(conversationState);
            Timber.d("Resuming conversation with: " + conversationState.toString());
        }

        return requestInfo;
    }

    public void setConversationState(JsonNode conversationState) {
        this.conversationState = conversationState;
    }
}
