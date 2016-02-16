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
    public void parseResponse(HoundResponse response){
        HoundifyResponse houndifyResponse =  new Gson().fromJson(response.getResults().get(0)
                .getJsonNode().get("NativeData").toString(), HoundifyResponse.class);
        Log.e("houndify response!", response.getResults().get(0).getJsonNode().toString());
        Log.e("Parsed response!", houndifyResponse.getTracks().get(0).getAlbumName());
        //            // We put pretty printing JSON on a separate thread as the server JSON can be quite large and will stutter the UI
//            JsonNode tracks = response.getResults().get(0).getNativeData().get("Tracks").get(0);
//            JsonNode thirdPartyInfo = tracks.get("MusicThirdPartyIds");
//            String spotifyId;
//            for(final JsonNode infoNode: thirdPartyInfo){
//                if(infoNode.get("MusicThirdParty").get("Name").textValue().equals("Spotify")){
//                    spotifyId = infoNode.get("Ids").get(0).textValue();
////                    mPlayer.play(spotifyId);
//                    break;
//                }
//            }

//            // Not meant to be configuration change proof, this is just a demo
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String message = "";
//                    try {
//                        message += "Response\n\n" + new JSONObject(info.getContentBody()).toString(4);
//                    }
//                    catch (final JSONException ex) {
////                        textView.setText("Bad JSON\n\n" + response);
//                        message += "Bad JSON\n\n" + response;
//                    }
//
//                    final String finalMessage = message;
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            textView.setText(finalMessage);
//                        }
//                    });
//                }
//            }).start();
    }
}
