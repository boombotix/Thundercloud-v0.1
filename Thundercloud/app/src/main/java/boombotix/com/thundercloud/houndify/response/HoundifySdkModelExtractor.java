package boombotix.com.thundercloud.houndify.response;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.core.model.sdk.CommandResult;
import com.hound.core.model.sdk.HoundResponse;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Class to extract houndify models from the JSON response that the SDK kicks back
 *
 * Created by kriedema on 6/10/16.
 */
public class HoundifySdkModelExtractor implements HoundifyModelExtractor {

    /**
     * Extracts the conversation state from a response. This is a JSON object that needs to be
     * sent back to the server in order to continue a conversation with a user.
     *
     * @param response the houndify response to extract the converation state from
     * @return
     *  JsonNode the JsonNode containing the conversation state object
     */
    @DebugLog
    @Override
    public JsonNode extractConversationState(@Nullable HoundResponse response) {
        List<CommandResult> commandResults = this.extractCommandResults(response);

        if(commandResults == null) return null;

        CommandResult commandResult = commandResults.get(0);

        return commandResult.getConversationState();
    }

    /**
     * Extracts the native data from the response. This is a JSON object representing the
     * results of different type of commands. Depending on the command given, this will need to
     * be deserialized as a different object.
     *
     * @param response the houdnify response to extract the native data from
     * @return
     *  JsonNode the JsonNode containing the native data extracted from the response
     */
    @DebugLog
    @Override
    public JsonNode extractNativeData(@Nullable HoundResponse response) {
        if(response == null) return null;
        if(response.getResults().size() == 0) return null;
        if(response.getResults().get(0) == null) return null;

        return response.getResults().get(0).getNativeData();
    }

    /**
     * Extracts a list of CommandResults from teh response. The command results are the results
     * of the command given, each of which contain native data. There will often only be one
     * command result, but the server can return multiple results.
     *
     * @param response the response to extract the command results from
     * @return
     *  List<CommandResult> the list of command results returned by the server
     */
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
