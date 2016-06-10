package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.core.model.sdk.CommandResult;
import com.hound.core.model.sdk.HoundResponse;

import java.util.List;

/**
 * Created by kriedema on 6/10/16.
 */
public interface HoundifyModelExtractor {

    @Nullable
    JsonNode extractConversationState(@Nullable HoundResponse response);

    @Nullable
    JsonNode extractNativeData(@Nullable HoundResponse response);

    @Nullable
    List<CommandResult> extractCommandResults(@Nullable HoundResponse response);
}
