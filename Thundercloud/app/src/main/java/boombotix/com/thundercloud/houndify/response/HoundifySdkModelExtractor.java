package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.core.model.sdk.CommandResult;
import com.hound.core.model.sdk.HoundResponse;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by kriedema on 6/10/16.
 */
public class HoundifySdkModelExtractor implements HoundifyModelExtractor {

    private static final String NATIVE_DATA_NODE = "NativeData";

    @DebugLog
    @Override
    public JsonNode extractConversationState(@Nullable HoundResponse response) {
        List<CommandResult> commandResults = this.extractCommandResults(response);

        if(commandResults == null) return null;

        CommandResult commandResult = commandResults.get(0);

        return commandResult.getConversationState();
    }

    @DebugLog
    @Override
    public JsonNode extractNativeData(@Nullable HoundResponse response) {
        if(response == null) return null;
        if(response.getResults().size() == 0) return null;
        if(response.getResults().get(0).getJsonNode() == null) return null;

        return response.getResults().get(0).getJsonNode().get(NATIVE_DATA_NODE);
    }

    @DebugLog
    @Override
    public List<CommandResult> extractCommandResults(@Nullable HoundResponse response) {
        if(response == null) return null;
        if(response.getResults().size() == 0) return null;

        List<CommandResult> commandResultList = new ArrayList<>();

        for(CommandResult result : response.getResults()){
            commandResultList.add(result);
        }

        return commandResultList;
    }
}
